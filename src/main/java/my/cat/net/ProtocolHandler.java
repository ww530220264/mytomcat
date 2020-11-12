package my.cat.net;

import java.util.concurrent.Executor;

public interface ProtocolHandler {

    Adapter getAdapter();
    void setAdapter(Adapter adapter);
    Executor getExecutor();
    void init() throws Exception;
    void start() throws Exception;
    void pause() throws Exception;
    void resume() throws Exception;
    void stop() throws Exception;
    void destorpy() throws Exception;
    void closeServerSocketGraceful();
}
