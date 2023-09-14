package com.jh.sgs.text;

import com.jh.sgs.core.InteractiveEvent;
import com.jh.sgs.core.interfaces.MessageReceipt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FileMessageConsoleControlReceipt implements MessageReceipt {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private PrintStream printStream;

    public FileMessageConsoleControlReceipt(File file) {
        try {
            this.printStream = new PrintStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void personal(int player, String message) {
        Util.printColor(printStream, 34, 3, LocalDateTime.now().format(formatter) + "  ");
        Util.printlnColor(printStream, 35, 3, player + "  消息：\t" + message);
    }

    @Override
    public void global(String message) {
        Util.printColor(printStream, 34, 3, LocalDateTime.now().format(formatter) + "  ");
        Util.printlnColor(printStream, 35, 3, "全局消息：\t" + message);
    }

    @Override
    public void system(List<InteractiveEvent> interactiveEvents) {
        for (InteractiveEvent interactiveEvent : interactiveEvents) {
            EventDispose.disposal(interactiveEvent);
        }

    }

    @Override
    public String name() {
        return "文件信息控制台处理回调器";
    }
}
