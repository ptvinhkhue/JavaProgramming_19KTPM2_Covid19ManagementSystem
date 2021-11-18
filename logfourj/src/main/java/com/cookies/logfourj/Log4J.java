/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cookies.logfourj;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author ptvin
 */
public class Log4J {
    private static final Logger logger = LogManager.getLogger(Log4J.class);

    public static void main( String[] args ) {
        System.out.println( "Hello, user!" );
        logger.trace("We've just greeted the user!");
        logger.debug("We've just greeted the user!");
        logger.info("We've just greeted the user!");
        logger.warn("We've just greeted the user!");
        logger.error("We've just greeted the user!");
        logger.fatal("We've just greeted the user!");
    }
}
