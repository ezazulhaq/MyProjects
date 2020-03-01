package com.haa.chatBot;

import com.botscrew.botframework.annotation.ChatEventsProcessor;
import com.botscrew.botframework.annotation.Location;
import com.botscrew.botframework.annotation.Postback;
import com.botscrew.botframework.annotation.Text;
import com.botscrew.messengercdk.model.MessengerUser;
import com.botscrew.messengercdk.model.incomming.Coordinates;
import com.botscrew.messengercdk.model.outgoing.builder.QuickReplies;
import com.botscrew.messengercdk.model.outgoing.builder.SenderAction;
import com.botscrew.messengercdk.model.outgoing.element.quickreply.PostbackQuickReply;
import com.botscrew.messengercdk.model.outgoing.element.quickreply.QuickReply;
import com.botscrew.messengercdk.service.Sender;
import com.botscrew.messengercdk.service.Messenger;
import org.springframework.beans.factory.annotation.Autowired;

@ChatEventsProcessor
public class MainHandler {

    private final Sender sender;
    private final Messenger messenger;

    @Autowired
    public MainHandler(Sender sender, Messenger messenger) {
        this.sender = sender;
        this.messenger = messenger; 
    }

    @Text
    public void handleText(MessengerUser user) {

        String name = messenger.getProfile(user.getChatId()).getFirstName();
        String message = "Hi " + name +  "! Would you like to find a taxi?";
        QuickReply yesReply = new PostbackQuickReply("Yes!", "FIND_TAXI");

        sender.send(
                QuickReplies.builder()
                        .user(user)
                        .text(message)
                        .addQuickReply(yesReply)
                        .build());
    }

    @Postback("FIND_TAXI")
    public void handleTaxiRequest(MessengerUser user) {

        sender.send(QuickReplies.builder()
                .user(user)
                .text("Send me your location, please. I'll look for it \uD83D\uDE09")
                .location()
                .build()
        );
    }

    @Location
    public void handleLocation(MessengerUser user, Coordinates coordinates) {

        sender.send(user, "Thanks! I'm working on it!");
        sender.send(SenderAction.typingOn(user));
        //looking
        sender.send(user, "Unfortunately there are no cars for now\uD83D\uDE1E \n I will let you know if something appears", 5000);
    }

}