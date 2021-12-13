package com.redhat.workshop.util;

public class isNumberCheck {
    
        public boolean isNumber(String val) {
            try {
                Integer.parseInt(val);
                return true;
            } catch (NumberFormatException nfex) {
                return false;
            }
        }
    }