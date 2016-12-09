package com.github.gwtd3.api.core;

import com.github.gwtd3.api.Coords;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsDate;

/**
 * A {@link Value} is an object wrapping a primitive or a complex value.
 * <p>
 * A {@link Value} is returned from many gwt-d3 functions.
 * <p>
 * The wrapped value can be retrieved from the numerous as*() methods.
 * <p>
 *
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 *
 */
public class Value extends JavaScriptObject {

    protected Value() {
        super();
    }

    /**
     * Wraps the given object into a {@link Value}.
     *
     * @param o
     *            the object to be wrapped as a Value
     * @return the value
     */
    public static final native Value create(Object o)/*-{
		return {
			datum : o
		};
    }-*/;

    /**
     * Create a {@link Value} instance from the value of the named property of
     * the given object.
     *
     * @param object
     * @param propertyName
     * @return
     */
    public static final native Value create(JavaScriptObject object,
            String propertyName)/*-{
		return {
			datum : object[propertyName]
		};
    }-*/;

    /**
     * Cast and return the wrapped value.
     * <p>
     *
     * @return the value
     */
    public final native boolean asBoolean()/*-{
		return this.datum instanceof Boolean ? this.datum.valueOf()
				: !!this.datum;
    }-*/;

    /**
     * Cast and return the wrapped value.
     * <p>
     *
     * @return the value
     */
    public final native byte asByte()/*-{
		return ~~this.datum;
    }-*/;

    /**
     * Cast and return the wrapped value.
     * <p>
     *
     * @return the value
     */
    public final native char asChar()/*-{
		return ~~this.datum;
    }-*/;

    public final native JsDate asJsDate()/*-{
		return this.datum instanceof Date ? this.datum : new Date(this.datum);
    }-*/;

    /**
     * Cast and return the wrapped value.
     * <p>
     *
     * @return the value
     */
    public final native double asDouble()/*-{
		return this.datum - 0;
    }-*/;

    /**
     * Cast and return the wrapped value.
     * <p>
     *
     * @return the value
     */
    public final native float asFloat()/*-{
		return this.datum - 0;
    }-*/;

    /**
     * Cast and return the wrapped value.
     * <p>
     *
     * @return the value
     */
    public final native int asInt()/*-{
		return ~~this.datum;
    }-*/;

    /**
     * Cast and return the value.
     * <p>
     *
     * @return the value
     */
    public final long asLong() {
        return (long) asDouble();
    }

    /**
     * Cast and return the wrapped value.
     * <p>
     *
     * @return the value
     */
    public final native short asShort()/*-{
		return ~~this.datum;
    }-*/;

    /**
     * Return the value casted to a String.
     *
     * @return the value
     */
    public final native String asString()/*-{
		return this.datum == null ? null : '' + this.datum;
    }-*/;

    /**
     * Return the value casted to a {@link Coords} object.
     * <p>
     *
     * @return the coords
     */
    public final native Coords asCoords()/*-{
		return this.datum;
    }-*/;

    /**
     * Cast and return the wrapped value, if possible.
     * <p>
     *
     * @throws ClassCastException
     *             if the value cannot be converted in T
     *
     * @return the value
     */
    public final native <T> T as()/*-{
		return this.datum;
    }-*/;

    /**
     * Cast and return the wrapped value.
     *
     * @param clazz
     *            the clazz to cast to
     * @return the casted instance
     */
    @SuppressWarnings("unchecked")
    public final <T> T as(final Class<T> clazz) {
        return (T) as();
    }

    /**
     *
     * @return true if the value is not undefined in the Javascript sense
     *
     */
    public final native boolean isDefined()/*-{
		return typeof (this.datum) != "undefined";
    }-*/;

    /**
     * @return true if the value is undefined in the Javascript sense
     */
    public final native boolean isUndefined()/*-{
		return typeof (this.datum) == "undefined";
    }-*/;

    public final native boolean isNull()/*-{
		return this.datum === null;
    }-*/;

    public final native boolean isString()/*-{
		return (typeof this.datum == 'string' || this.datum instanceof String);
    }-*/;

    // public final native boolean isNumber()/*-{
    // //return this.datum === string;
    // var o = this.datum;
    // return !isNaN(o - 0) && o !== null && o !== "" && o !== false && o !==
    // true;
    // }-*/;

    public final native boolean isFunction()/*-{
		return typeof (this.datum) == 'function';
    }-*/;

    /**
     * @return true if the value is a Javascript boolean value, a Javascript Boolean object or a Java {@link Boolean}
     *         instance.
     */
    public final native boolean isBoolean()/*-{
		return typeof (this.datum) === "boolean" || this.datum instanceof Boolean;
    }-*/;

    /**
     * Return the property of this object as a {@link Value}.
     * <p>
     * This method result is never non-null. The returned value may then be tested for nullity (with {@link #isNull()})
     * or for undefinition (with {@link #isUndefined()}).
     * <p>
     *
     * @param propertyName
     *            the name of the property to get
     * @return the property value as a value.
     */
    public final native Value getProperty(String propertyName)/*-{
		return {
			datum : this.datum[propertyName]
		};
    }-*/;

    /**
     * The result of the typeof operator.
     * <p>
     *
     * @return the String returned by a call to typeof
     */
    public final native String typeof()/*-{
		return typeof this.datum;
    }-*/;
}
