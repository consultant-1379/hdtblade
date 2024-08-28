/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.domain;

/**
 *
 * @author escralp
 */
public class QuickLink extends HdtItem {

    /**
     *
     * @param linkDescription
     * @param linkUrl
     *
     * <b>NOTE</b>: The following mapping of properties between QuickLink and HdtItem
     * objects takes place:
     * linkDescription ===> name
     * linkUrl         ===> description
     */
    public QuickLink(String linkDescription, String linkUrl) {
        super(linkDescription, linkUrl);
    }
}
