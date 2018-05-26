package mplus.hackathon.config.websocket.utils.errors;

/**
 * Created by danul on 07.10.2017.
 */
public class NullPointerCategory extends Throwable {

    public NullPointerCategory() {
        super("null CategoryMessage in categoryMessageList");
    }
}
