package com.jh.sgs.text;

import com.jh.sgs.base.interfaces.InteractiveEvent;
import com.jh.sgs.base.interfaces.MessageReceipt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AutoMessageReceipt implements MessageReceipt {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private PrintStream printStream;

    public AutoMessageReceipt(File file) {
        try {
            this.printStream = new PrintStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void personal(int player, String message) {
        printStream.println(LocalDateTime.now().format(formatter)+" player"+player + "消息：\t" + message);
    }

    @Override
    public void global(String message) {
        printStream.println(LocalDateTime.now().format(formatter)+" 全局消息：\t\t" + message);
    }

    @Override
    public void system(List<InteractiveEvent> interactiveEventers) {
        for (InteractiveEvent interactiveEventer : interactiveEventers) {
            interactiveEventer.cancel();
        }
    }

    @Override
    public String name() {
        return "文件无处理文本回调器";
    }
}
