/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package framework;

/**
 *
 * @author sathyam
 */
class Record implements java.io.Serializable
{
        long rowid;
	String groupid;
	String key, value, user, datatype;
	long timestamp;
}