package com.sketchengine.manatee;/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

public class EvalQueryException {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected EvalQueryException(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(EvalQueryException obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        manateeJNI.delete_EvalQueryException(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public EvalQueryException(String name) {
    this(manateeJNI.new_EvalQueryException(name), true);
  }

  public String __str__() {
    return manateeJNI.EvalQueryException___str__(swigCPtr, this);
  }

}
