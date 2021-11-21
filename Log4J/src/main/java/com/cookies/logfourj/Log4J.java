/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cookies.logfourj;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;

/**
 *
 * @author ptvin
 */
public class Log4J {
    //static final Level ALERT = Level.forName("ALERT", 250);
    private static final Logger logger = LogManager.getLogger(Log4J.class);

    public static void main( String[] args ) {
        System.out.println( "A log was written!" );
        logger.trace("This is a TRACE level log!");
        logger.debug("This is a DEBUG level log!");
        logger.info("This is an INFO level log!");
        logger.warn("This is a WARN level log!");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            System.out.println("InterruptedException");
        }
        //logger.log(ALERT, "This is an ALERT level log!");
        logger.error("This is an ERROR level log!");
        logger.fatal("This is a FATAL level log!");
    }
}