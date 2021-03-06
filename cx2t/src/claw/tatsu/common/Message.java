/*
 * This file is released under terms of BSD license
 * See LICENSE file for more information
 */
package claw.tatsu.common;

import claw.tatsu.xcodeml.error.XanalysisError;
import claw.tatsu.xcodeml.xnode.common.XcodeProgram;
import xcodeml.util.XmOption;

import java.util.List;

/**
 * Centralized compiler output messages.
 *
 * @author clementval
 */
public final class Message {

  private static final String ERROR_PREFIX = "claw-error: ";
  private static final String WARNING_PREFIX = "claw warning: ";

  // Avoid potential instantiation of this class
  private Message() {
  }

  /**
   * Display debug message if debugging is activated.
   *
   * @param message Message to display.
   */
  public static void debug(String message) {
    if(XmOption.isDebugOutput()) {
      System.err.println(message);
    }
  }

  /**
   * Print all messages in the given list with the prefix.
   *
   * @param prefix   Prefix for the message.
   * @param messages List of messages to display.
   */
  private static void printMessages(String prefix, List<XanalysisError> messages)
  {
    for(XanalysisError message : messages) {
      if(message.getLine() == 0) {
        System.err.println(String.format("%s %s, line: -", prefix,
            message.getMessage()));
      } else {
        System.err.println(String.format("%s %s, line: %s", prefix,
            message.getMessage(), message.getConcatLines()));
      }
    }
    messages.clear();
  }

  /**
   * Display all warnings stored in translation unit.
   *
   * @param translationUnit Current translation unit.
   */
  public static void warnings(XcodeProgram translationUnit) {
    if(translationUnit != null) {
      printMessages(WARNING_PREFIX, translationUnit.getWarnings());
    }
  }

  /**
   * Display all errors stored in translation unit.
   *
   * @param translationUnit Current translation unit.
   */
  public static void errors(XcodeProgram translationUnit) {
    if(translationUnit != null) {
      printMessages(ERROR_PREFIX, translationUnit.getErrors());
    }
  }
}
