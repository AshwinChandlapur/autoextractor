package edu.usc.irds.autoext.tree;

import edu.usc.irds.autoext.base.SimilarityComputer;
import edu.usc.irds.autoext.utils.ParseUtils;
import org.junit.Test;
import org.w3c.dom.Document;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by tg on 1/16/16.
 */
public class GrossSimComputerTest {

    @Test
    public void testCompute() throws Exception {

        SimilarityComputer<String> caseSensitiveComputer = new SimilarityComputer<String>() {
            @Override
            public double compute(String obj1, String obj2) {
                return obj1.equals(obj2) ? 1.0 : 0.0;
            }
        };

        SimilarityComputer<String> caseInsensitiveComputer = new SimilarityComputer<String>() {
            @Override
            public double compute(String obj1, String obj2) {
                return obj1.toLowerCase().equals(obj2.toLowerCase()) ? 1.0 : 0.0;
            }
        };

        GrossSimComputer<String> computer = new GrossSimComputer<>(Arrays.asList(caseSensitiveComputer, caseInsensitiveComputer), Arrays.asList(0.5, 0.5));
        assertEquals(1.0, computer.compute("abcd", "abcd"), 0.00001);
        assertEquals(0.5, computer.compute("abcd", "ABCD"), 0.00001);
        assertEquals(0.0, computer.compute("aaa", "bbbb"), 0.00001);
    }

    @Test
    public void testCreateWebSimilarityComputer() throws Exception {
        GrossSimComputer<TreeNode> simComputer = GrossSimComputer.createWebSimilarityComputer(0.5);

        Document doc1 = ParseUtils.parseFile("src/test/resources/html/simple/1.html");
        Document doc2 = ParseUtils.parseFile("src/test/resources/html/simple/2.html");
        Document doc3 = ParseUtils.parseFile("src/test/resources/html/simple/3.html");

        TreeNode tree1 = new TreeNode(doc1.getDocumentElement(), null);
        TreeNode tree2 = new TreeNode(doc2.getDocumentElement(), null);
        TreeNode tree3 = new TreeNode(doc3.getDocumentElement(), null);
        assertEquals(1.0, simComputer.compute(tree1, tree1), 0.0001);
        assertEquals(1.0, simComputer.compute(tree2, tree2), 0.0001);
        assertEquals(1.0, simComputer.compute(tree3, tree3), 0.0001);
        assertEquals(0.9, simComputer.compute(tree1, tree2), 0.1);
        assertEquals(0.3, simComputer.compute(tree1, tree3), 0.1);
        assertEquals(0.3, simComputer.compute(tree2, tree3), 0.1);
    }
}