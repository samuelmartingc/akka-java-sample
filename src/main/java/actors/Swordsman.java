package actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by samuel on 24/11/16.
 */
public class Swordsman extends UntypedActor{
    public enum Message {
        SWORD,
        GREETINGS
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void preStart() {
        final ActorRef blacksmith = getContext().actorOf(Props.create(Blacksmith.class), "blacksmith");
        blacksmith.tell(Blacksmith.Message.FORGE_SWORD, getSelf());
        blacksmith.tell(Message.GREETINGS, getSelf());
    }

    @Override
    public void onReceive(Object o) {
        log.info("[Swordsman] has received the message: \"{}\".", o);

        if (o == Message.GREETINGS) {
            log.info("[Swordsman] what a lovely day to protect the kingdom: \"{}\".", o);
        }
        if (o == Message.SWORD) {
            log.info("[Swordsman] finally, now with my new sword I can fight the bandits and rescue the princess \"{}\".", o);
            getContext().stop(getSelf());
        } else {
            unhandled(o);
        }
    }
}
