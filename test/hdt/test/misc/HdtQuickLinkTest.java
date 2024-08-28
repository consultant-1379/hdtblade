/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.test.misc;

import hdt.dao.QuickLinkDao;
import hdt.domain.QuickLink;
import hdt.test.BaseHdtTest;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author escralp
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class HdtQuickLinkTest extends BaseHdtTest {

    @Autowired
    private QuickLinkDao qLDao;

    @Test
    public void testQuickLinks() {
        List<QuickLink> qLinks = qLDao.findAll();

        for (QuickLink q : qLinks) {
            System.out.println("Quicklink description: " + q.getName() + ", Quicklink URL: " + q.getDescription());
        }
    }
}
