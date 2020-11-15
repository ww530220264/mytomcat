package my.cat.net;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * @author wangwei@huixiangtech.cn
 * @version 1.0
 * @className Processor
 * @description TODO
 * @date 2020/11/15-17:31
 **/
public interface Processor {

    void process(SelectionKey key) throws IOException;
}
