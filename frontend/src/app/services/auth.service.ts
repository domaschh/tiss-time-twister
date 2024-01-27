import { Injectable } from '@angular/core';
import { AuthRequest } from '../dtos/auth-request';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { tap } from 'rxjs/operators';
import { jwtDecode } from 'jwt-decode';
import { Globals } from '../global/globals';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private authBaseUri: string = this.globals.backendUri + '/authentication';
  private registerBaseUri: string = this.globals.backendUri + '/register';

  constructor(
    private httpClient: HttpClient,
    private globals: Globals,) {
  }


  /**
  * Register the user. If it was successful, a valid JWT token will be stored
  *
  * @param userRegistrationData User data
  */
  registerUser(userRegistrationData: any): Observable<string> {
    return this.httpClient.post(this.registerBaseUri, userRegistrationData, { responseType: 'text' })
      .pipe(
        tap((AuthenticatorResponse: string) => this.setToken(AuthenticatorResponse))
      );
  }

  /**
   * Login in the user. If it was successful, a valid JWT token will be stored
   *
   * @param authRequest User data
   */
  loginUser(authRequest: AuthRequest): Observable<string> {
    return this.httpClient.post(this.authBaseUri, authRequest, { responseType: 'text' })
      .pipe(
        tap((authResponse: string) => this.setToken(authResponse))
      );
  }


  /**
   * Check if a valid JWT token is saved in the localStorage
   */
  isLoggedIn() {
    let validUntil = this.getTokenExpirationDate(this.getToken()).valueOf();
    let now = Date.now();

    let tokenStillValid = validUntil > now;
    return !!this.getToken() && tokenStillValid;
  }

  logoutUser() {
    console.log('Logout');
    localStorage.removeItem('authToken');
  }

  getToken() {
    return localStorage.getItem('authToken');
  }

  getUsername() {
    return jwtDecode(this.getToken())["sub"].split("@")[0];
  }

  getEmail() {
    return jwtDecode(this.getToken())["sub"];
  }

  /**
   * Returns the user role based on the current token
   */
  getUserRole() {
    if (this.getToken() != null) {
      const decoded: any = jwtDecode(this.getToken());
      const authInfo: string[] = decoded.rol;
      if (authInfo.includes('ROLE_ADMIN')) {
        return 'ADMIN';
      } else if (authInfo.includes('ROLE_USER')) {
        return 'USER';
      }
    }
    return 'UNDEFINED';
  }

  requestPasswordReset(email: string): Observable<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    const params = new HttpParams().set('email', email);
    return this.httpClient.post(`${this.globals.backendUri}/forgotPassword?email=${encodeURIComponent(email)}`, {})
  }

  resetPassword(token: string, newPassword: string, confirmPassword: string): Observable<any> {
    return this.httpClient.post(`${this.globals.backendUri}/resetPassword`, { token, newPassword, confirmPassword });
  }

  private setToken(authResponse: string) {
    localStorage.setItem('authToken', authResponse);
  }

  private getTokenExpirationDate(token: string): Date {
    const decoded: any = jwtDecode(token);
    if (decoded.exp === undefined) {
      return null;
    }

    const date = new Date(0);
    date.setUTCSeconds(decoded.exp);
    return date;
  }

}
