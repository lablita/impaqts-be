package com.sketchengine.manatee;/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

public class ConcNotFound {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected ConcNotFound(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(ConcNotFound obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        manateeJNI.delete_ConcNotFound(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public ConcNotFound(String name) {
    this(manateeJNI.new_ConcNotFound(name), true);
  }

  public String __str__() {
    return manateeJNI.ConcNotFound___str__(swigCPtr, this);
  }

}
