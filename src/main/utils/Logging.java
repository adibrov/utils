package utils;

import java.util.logging.*;

public class Logging {
    public static Logger mLogger;

    public static Logger getLogger(String pName) {
        if (mLogger == null) {
            mLogger = Logger.getLogger(pName);
            mLogger.setUseParentHandlers(false);
            mLogger.setLevel(Level.ALL);
            Handler lHandler = new MyHandler();
//            Handler lHandler = new ConsoleHandler();
            Formatter lFormatter = new Formatter() {
                @Override
                public String format(LogRecord record) {
                    String[] str = record.getSourceClassName().split("\\.");
                    return "[" + str[str.length - 1] + "]: " + record.getMessage()+"\n";
                }
            };

            lHandler.setFormatter(lFormatter);
//            lHandler.
            mLogger.addHandler(lHandler);

//            new ConsoleHandler()
        }
        return mLogger;
    }

    public static void setLogger(Logger pLogger){
        mLogger = pLogger;



        mLogger.setUseParentHandlers(false);
        mLogger.setLevel(Level.ALL);
        Handler lHandler = new MyHandler();

//            Handler lHandler = new ConsoleHandler();
        Formatter lFormatter = new Formatter() {
            @Override
            public String format(LogRecord record) {
                String[] str = record.getSourceClassName().split("\\.");
                return "[" + str[str.length - 1] + "]: " + record.getMessage()+"\n";
            }
        };

        lHandler.setFormatter(lFormatter);
//            lHandler.
        mLogger.addHandler(lHandler);
    }
}
