package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.DetailedMessageDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.MessageInquiryDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.SimpleMessageDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Message;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-27T19:47:47+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class MessageMapperImpl implements MessageMapper {

    @Override
    public SimpleMessageDto messageToSimpleMessageDto(Message message) {
        if ( message == null ) {
            return null;
        }

        SimpleMessageDto simpleMessageDto = new SimpleMessageDto();

        simpleMessageDto.setId( message.getId() );
        simpleMessageDto.setPublishedAt( message.getPublishedAt() );
        simpleMessageDto.setTitle( message.getTitle() );
        simpleMessageDto.setSummary( message.getSummary() );

        return simpleMessageDto;
    }

    @Override
    public List<SimpleMessageDto> messageToSimpleMessageDto(List<Message> message) {
        if ( message == null ) {
            return null;
        }

        List<SimpleMessageDto> list = new ArrayList<SimpleMessageDto>( message.size() );
        for ( Message message1 : message ) {
            list.add( messageToSimpleMessageDto( message1 ) );
        }

        return list;
    }

    @Override
    public DetailedMessageDto messageToDetailedMessageDto(Message message) {
        if ( message == null ) {
            return null;
        }

        DetailedMessageDto detailedMessageDto = new DetailedMessageDto();

        detailedMessageDto.setId( message.getId() );
        detailedMessageDto.setPublishedAt( message.getPublishedAt() );
        detailedMessageDto.setTitle( message.getTitle() );
        detailedMessageDto.setSummary( message.getSummary() );
        detailedMessageDto.setText( message.getText() );

        return detailedMessageDto;
    }

    @Override
    public Message detailedMessageDtoToMessage(DetailedMessageDto detailedMessageDto) {
        if ( detailedMessageDto == null ) {
            return null;
        }

        Message message = new Message();

        message.setId( detailedMessageDto.getId() );
        message.setPublishedAt( detailedMessageDto.getPublishedAt() );
        message.setTitle( detailedMessageDto.getTitle() );
        message.setSummary( detailedMessageDto.getSummary() );
        message.setText( detailedMessageDto.getText() );

        return message;
    }

    @Override
    public Message messageInquiryDtoToMessage(MessageInquiryDto messageInquiryDto) {
        if ( messageInquiryDto == null ) {
            return null;
        }

        Message message = new Message();

        message.setTitle( messageInquiryDto.getTitle() );
        message.setSummary( messageInquiryDto.getSummary() );
        message.setText( messageInquiryDto.getText() );

        return message;
    }

    @Override
    public MessageInquiryDto messageToMessageInquiryDto(Message message) {
        if ( message == null ) {
            return null;
        }

        MessageInquiryDto messageInquiryDto = new MessageInquiryDto();

        messageInquiryDto.setTitle( message.getTitle() );
        messageInquiryDto.setSummary( message.getSummary() );
        messageInquiryDto.setText( message.getText() );

        return messageInquiryDto;
    }
}
