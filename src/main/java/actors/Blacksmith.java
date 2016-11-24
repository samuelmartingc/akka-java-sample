package actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by samuel on 24/11/16.
 */
public class Blacksmith extends UntypedActor {

    public enum Message {
        FORGE_SWORD,
        MATERIALS
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private ActorRef swordsman;
    private ActorRef miner;


    @Override
    public void preStart() {
        miner = getContext().actorOf(Props.create(Miner.class), "miner");
    }

    @Override
    public void onReceive(Object o) throws InterruptedException {
        log.info("[Blacksmith] has received a message: \"{}\".", o);

        if (o == Message.FORGE_SWORD) {
            swordsman = getSender();
            miner.tell(Miner.Message.OBTAIN_MINERALS, getSelf());
        } else if (o == Message.MATERIALS) {
            log.info("[Blacksmith] is forging sword...");
            forgeSword();
            log.info("[Blacksmith] has finished his job, a beautiful shiny longsword.");
            swordsman.tell(Swordsman.Message.SWORD, getSelf());
        } else {
            unhandled(o);
        }
    }

    private void forgeSword() {
        log.info("[Blacksmith] Forging...");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {}
        log.info("[Blacksmith] Finished! Run boy, give the sword to its new master");
    }
}
