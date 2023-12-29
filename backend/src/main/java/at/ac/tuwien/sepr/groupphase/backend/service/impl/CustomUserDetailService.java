package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserLoginDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserRegistrationDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.exception.EmailAlreadyExistsException;
import at.ac.tuwien.sepr.groupphase.backend.exception.InvalidEmailException;
import at.ac.tuwien.sepr.groupphase.backend.exception.InvalidPasswordException;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.exception.PasswordDoesNotMatchEmailException;
import at.ac.tuwien.sepr.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepr.groupphase.backend.security.JwtTokenizer;
import at.ac.tuwien.sepr.groupphase.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CustomUserDetailService implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenizer jwtTokenizer;

    @Autowired
    public CustomUserDetailService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenizer jwtTokenizer) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenizer = jwtTokenizer;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LOGGER.debug("Load all user by email");
        try {
            ApplicationUser applicationUser = findApplicationUserByEmail(email);

            List<GrantedAuthority> grantedAuthorities;
            if (applicationUser.getAdmin()) {
                grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
            } else {
                grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_USER");
            }

            return new User(applicationUser.getEmail(), applicationUser.getPassword(), grantedAuthorities);
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage(), e);
        }
    }

    @Override
    public ApplicationUser findApplicationUserByEmail(String email) {
        LOGGER.debug("Find application user by email");
        ApplicationUser applicationUser = userRepository.findUserByEmail(email);
        if (applicationUser != null) {
            return applicationUser;
        }
        throw new NotFoundException(String.format("Could not find the user with the email address %s", email));
    }

    @Override
    public String login(UserLoginDto userLoginDto) {
        try {
            UserDetails userDetails = loadUserByUsername(userLoginDto.getEmail());
            if (userDetails == null
                || !userDetails.isAccountNonExpired()
                || !userDetails.isAccountNonLocked()
                || !userDetails.isCredentialsNonExpired()) {
                throw new NotFoundException("User not found with email: " + userLoginDto.getEmail());
            }

            if (!isValidPassword(userLoginDto.getPassword())) {
                throw new InvalidPasswordException("Password format is invalid");
            }
            if (passwordEncoder.matches(userLoginDto.getPassword(), userDetails.getPassword())) {
                List<String> roles = userDetails.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();
                return jwtTokenizer.getAuthToken(userDetails.getUsername(), roles);
            } else {
                throw new PasswordDoesNotMatchEmailException("Invalid password");
            }
        } catch (UsernameNotFoundException e) {
            LOGGER.error("Authentication failed for user: " + userLoginDto.getEmail(), e);
            throw new NotFoundException("User not found with email: " + userLoginDto.getEmail());
        }
    }

    @Override
    public String registerUser(UserRegistrationDto userRegistrationDto) throws EmailAlreadyExistsException, InvalidEmailException, InvalidPasswordException {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");

        if (userRepository.findUserByEmail(userRegistrationDto.getEmail()) != null) {
            throw new EmailAlreadyExistsException("An user already exists with this email.");
        }

        if (!isValidEmail(userRegistrationDto.getEmail())) {
            throw new InvalidEmailException("Email entered is not valid.");
        }

        if (!isValidPassword(userRegistrationDto.getPassword())) {
            throw new InvalidPasswordException("Password entered is not valid.");
        }
        ApplicationUser newUser = new ApplicationUser(
            userRegistrationDto.getEmail(),
            passwordEncoder.encode(userRegistrationDto.getPassword()),
            false
        );
        userRepository.save(newUser);
        return jwtTokenizer.getAuthToken(userRegistrationDto.getEmail(), roles);
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."
            + "[a-zA-Z0-9_+&*-]+)*@"
            + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
            + "A-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null) { return false; }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }
}
