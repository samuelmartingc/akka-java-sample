package actors;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by samuel on 24/11/16.
 */
public class Miner extends UntypedActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    public enum Message {
        OBTAIN_MINERALS
    }


    @Override
    public void onReceive(Object o) {
        log.info("[Miner] has received message: \"{}\".", o);

        if (o == Message.OBTAIN_MINERALS) {
            log.info("[Miner] is obtaining minerals");
            try {
                obtainMinerals();
            } catch (InterruptedException e) {}
            log.info("[Miner] has the best minerals ready for you.");
            getSender().tell(Blacksmith.Message.MATERIALS, getSelf());
        } else {
            unhandled(o);
        }
    }

    private void obtainMinerals() throws InterruptedException {
        log.info("[Miner] extracting minerals...");
        Thread.sleep(500);
        log.info("[Miner] we need more vespene gas...");
        Thread.sleep(1000);
        log.info("[Miner] Ready!");
    }

}
