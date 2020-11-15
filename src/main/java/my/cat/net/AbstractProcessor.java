package my.cat.net;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * @author wangwei@huixiangtech.cn
 * @version 1.0
 * @className AbstractProcessor
 * @description TODO
 * @date 2020/11/15-17:33
 **/
public class AbstractProcessor implements Processor {
    @Override
    public void process(SelectionKey key) throws IOException {
        //NOOP
    }
}
