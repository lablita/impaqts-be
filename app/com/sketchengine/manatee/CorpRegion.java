package com.sketchengine.manatee;/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

public class CorpRegion {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected CorpRegion(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CorpRegion obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        manateeJNI.delete_CorpRegion(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public CorpRegion(Corpus corp, String attra, String struca, boolean ignore_nondef) {
    this(manateeJNI.new_CorpRegion__SWIG_0(Corpus.getCPtr(corp), corp, attra, struca, ignore_nondef), true);
  }

  public CorpRegion(Corpus corp, String attra, String struca) {
    this(manateeJNI.new_CorpRegion__SWIG_1(Corpus.getCPtr(corp), corp, attra, struca), true);
  }

  public StrVector region(long frompos, long topos, char posdelim, char attrdelim) {
    return new StrVector(manateeJNI.CorpRegion_region__SWIG_0(swigCPtr, this, frompos, topos, posdelim, attrdelim), true);
  }

  public StrVector region(long frompos, long topos, char posdelim) {
    return new StrVector(manateeJNI.CorpRegion_region__SWIG_1(swigCPtr, this, frompos, topos, posdelim), true);
  }

  public StrVector region(long frompos, long topos) {
    return new StrVector(manateeJNI.CorpRegion_region__SWIG_2(swigCPtr, this, frompos, topos), true);
  }

}
