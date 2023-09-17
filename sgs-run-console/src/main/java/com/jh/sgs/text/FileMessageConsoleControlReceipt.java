package com.jh.sgs.text;

import com.jh.sgs.base.interfaces.InteractiveEvent;
import com.jh.sgs.base.interfaces.MessageReceipt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FileMessageConsoleControlReceipt implements MessageReceipt {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private PrintStream[] printStream;

    public FileMessageConsoleControlReceipt(File ...file) {
        printStream=new PrintStream[file.length];
        for (int i = 0; i < printStream.length; i++) {
            try {
                printStream[i]=new PrintStream(file[i]);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void personal(int player, String message) {
        Util.printColor(printStream[player+1], 34, 3, LocalDateTime.now().format(formatter) + "  ");
        Util.printlnColor(printStream[player+1], 35, 3, player + "  消息：\t" + message);
    }

    @Override
    public void global(String message) {
        Util.printColor(printStream[0], 34, 3, LocalDateTime.now().format(formatter) + "  ");
        Util.printlnColor(printStream[0], 35, 3, "全局消息：\t" + message);
    }

    @Override
    public void system(List<InteractiveEvent> interactiveEventers) {
        for (InteractiveEvent interactiveEventer : interactiveEventers) {
            EventDispose.disposal(interactiveEventer);
        }

    }

    @Override
    public String name() {
        return "文件信息控制台处理回调器";
    }

    @Override
    protected void finalize() throws Throwable {
        for (PrintStream stream : printStream) {
            stream.close();
        }
    }
}
