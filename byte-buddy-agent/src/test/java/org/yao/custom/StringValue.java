package org.yao.custom;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public @Retention(RetentionPolicy.RUNTIME)
@interface StringValue {
  String value();
}