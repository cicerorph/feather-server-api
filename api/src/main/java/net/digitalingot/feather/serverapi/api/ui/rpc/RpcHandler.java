package net.digitalingot.feather.serverapi.api.ui.rpc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to mark a method as an RPC handler. The value specifies the name of the RPC request that the
 * method handles.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcHandler {
  /**
   * The name of the RPC request that the annotated method handles.
   *
   * @return the RPC request name
   */
  String value();
}
