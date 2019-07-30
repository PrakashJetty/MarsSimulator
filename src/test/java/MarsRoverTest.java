import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Created by prakashjetty on 7/29/19.
 */
public class MarsRoverTest {
    public MarsRoverTest() {

    }

    @Before
    public void setup() {
        MarsRover.getBlockSet().clear();
        MarsRover.getBlockSet().addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6).stream().map(i -> {
            return new MarsRover.Position(i, 6);
        }).collect(Collectors.toSet()));
        MarsRover.getBlockSet().addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6).stream().map(i -> {
            return new MarsRover.Position(6, i);
        }).collect(Collectors.toSet()));
        MarsRover.getBlockSet().addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6).stream().map(i -> {
            return new MarsRover.Position(i, -1);
        }).collect(Collectors.toSet()));
        MarsRover.getBlockSet().addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6).stream().map(i -> {
            return new MarsRover.Position(-1, i);
        }).collect(Collectors.toSet()));
        MarsRover.getBlockSet().addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6).stream().map(i -> {
            return new MarsRover.Position(-1, -i);
        }).collect(Collectors.toSet()));
        MarsRover.getBlockSet().addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6).stream().map(i -> {
            return new MarsRover.Position(-i, -1);
        }).collect(Collectors.toSet()));

    }


    private boolean testPathEquality(Stack<MarsRover.Position> aresult, Stack<MarsRover.Position> eresult) {
        if (aresult.isEmpty() && eresult.isEmpty())
            return true;
        else if (!aresult.isEmpty() && !eresult.isEmpty()) {
            if (aresult.size() != eresult.size())
                return false;
            else {
                for (int i = 0; i <= aresult.size(); ++i) {
                    if (!aresult.pop().equals(eresult.pop()))
                        return false;
                }
                return true;
            }
        } else {
            return false;
        }

    }


    @Test
    public void testExplorePath1() {
        MarsRover.traverseCmd("PLACE", 0, 0);
        MarsRover.traverseCmd("BLOCK", 0, 2);
        Stack<MarsRover.Position> aresult = MarsRover.traverseCmd("EXPLORE", 0, 3);
        Stack<MarsRover.Position> eresult = new Stack<>();
        eresult.add(new MarsRover.Position(0, 0));
        eresult.add(new MarsRover.Position(0, 1));
        eresult.add(new MarsRover.Position(1, 1));
        eresult.add(new MarsRover.Position(1, 2));
        eresult.add(new MarsRover.Position(1, 3));
        eresult.add(new MarsRover.Position(0, 3));

        Assert.assertTrue(testPathEquality(aresult, eresult));
    }

    @Test
    public void testExplorePath21() {
        MarsRover.traverseCmd("PLACE", 0, 0);
        MarsRover.traverseCmd("BLOCK", 1, 2);
        MarsRover.traverseCmd("BLOCK", 1, 3);
        MarsRover.traverseCmd("BLOCK", 2, 1);
        MarsRover.traverseCmd("BLOCK", 3, 1);
        Stack<MarsRover.Position> aresult = MarsRover.traverseCmd("EXPLORE", 2, 2);

        Stack<MarsRover.Position> eresult = new Stack<>();
        eresult.add(new MarsRover.Position(0, 0));
        eresult.add(new MarsRover.Position(1, 0));
        eresult.add(new MarsRover.Position(2, 0));
        eresult.add(new MarsRover.Position(3, 0));
        eresult.add(new MarsRover.Position(4, 0));
        eresult.add(new MarsRover.Position(4, 1));
        eresult.add(new MarsRover.Position(4, 2));
        eresult.add(new MarsRover.Position(3, 2));
        eresult.add(new MarsRover.Position(2, 2));

        Assert.assertTrue(testPathEquality(aresult, eresult));
    }

    @Test
    public void testExplorePath22() {
        MarsRover.traverseCmd("PLACE", 0, 0);
        MarsRover.traverseCmd("BLOCK", 1, 2);
        MarsRover.traverseCmd("BLOCK", 2, 1);
        Stack<MarsRover.Position> aresult = MarsRover.traverseCmd("EXPLORE", 2, 2);

        Stack<MarsRover.Position> eresult = new Stack<>();
        eresult.add(new MarsRover.Position(0, 0));
        eresult.add(new MarsRover.Position(1, 0));
        eresult.add(new MarsRover.Position(2, 0));
        eresult.add(new MarsRover.Position(3, 0));
        eresult.add(new MarsRover.Position(3, 1));
        eresult.add(new MarsRover.Position(3, 2));
        eresult.add(new MarsRover.Position(2, 2));

        Assert.assertTrue(testPathEquality(aresult, eresult));
    }


    @Test
    public void testExplorePath3() {
        MarsRover.traverseCmd("PLACE", 0, 0);
        MarsRover.traverseCmd("BLOCK", 1, 2);
        Stack<MarsRover.Position> aresult = MarsRover.traverseCmd("EXPLORE", 2, 2);
        Stack<MarsRover.Position> eresult = new Stack<>();
        eresult.add(new MarsRover.Position(0, 0));
        eresult.add(new MarsRover.Position(1, 0));
        eresult.add(new MarsRover.Position(2, 0));
        eresult.add(new MarsRover.Position(2, 1));
        eresult.add(new MarsRover.Position(2, 2));


        Assert.assertTrue(testPathEquality(aresult, eresult));

    }

    @Test
    public void testExplorePath4() {
        MarsRover.traverseCmd("PLACE", 0, 0);
        MarsRover.traverseCmd("BLOCK", 2, 1);
        Stack<MarsRover.Position> aresult = MarsRover.traverseCmd("EXPLORE", 2, 2);

        Stack<MarsRover.Position> eresult = new Stack<>();
        eresult.add(new MarsRover.Position(0, 0));
        eresult.add(new MarsRover.Position(0, 1));
        eresult.add(new MarsRover.Position(0, 2));
        eresult.add(new MarsRover.Position(1, 2));
        eresult.add(new MarsRover.Position(2, 2));


        Assert.assertTrue(testPathEquality(aresult, eresult));
    }

    @Test
    public void testExplorePath5() {
        MarsRover.traverseCmd("PLACE", 2, 2);
        MarsRover.traverseCmd("BLOCK", 1, 2);
        MarsRover.traverseCmd("BLOCK", 2, 1);
        Stack<MarsRover.Position> aresult = MarsRover.traverseCmd("EXPLORE", 0, 0);


        Stack<MarsRover.Position> eresult = new Stack<>();
        eresult.add(new MarsRover.Position(2, 2));
        eresult.add(new MarsRover.Position(2, 3));
        eresult.add(new MarsRover.Position(1, 3));
        eresult.add(new MarsRover.Position(0, 3));
        eresult.add(new MarsRover.Position(0, 2));
        eresult.add(new MarsRover.Position(0, 1));
        eresult.add(new MarsRover.Position(0, 0));

        Assert.assertTrue(testPathEquality(aresult, eresult));
    }

    @Test
    public void testExplorePath6() {
        MarsRover.traverseCmd("PLACE", 0, 0);
        MarsRover.traverseCmd("BLOCK", 1, 2);
        MarsRover.traverseCmd("BLOCK", 2, 1);
        MarsRover.traverseCmd("BLOCK", 2, 3);
        MarsRover.traverseCmd("BLOCK", 3, 2);
        Stack<MarsRover.Position> aresult = MarsRover.traverseCmd("EXPLORE", 2, 2);
        Stack<MarsRover.Position> eresult = new Stack<>();


        Assert.assertTrue(testPathEquality(aresult, eresult));
    }

    @Test
    public void testExplorePath7() {
        MarsRover.traverseCmd("PLACE", 0, 0);
        MarsRover.traverseCmd("BLOCK", 1, 2);
        MarsRover.traverseCmd("BLOCK", 2, 1);
        MarsRover.traverseCmd("BLOCK", 2, 3);
        Stack<MarsRover.Position> aresult = MarsRover.traverseCmd("EXPLORE", 2, 2);

        Stack<MarsRover.Position> eresult = new Stack<>();
        eresult.add(new MarsRover.Position(0, 0));
        eresult.add(new MarsRover.Position(1, 0));
        eresult.add(new MarsRover.Position(2, 0));
        eresult.add(new MarsRover.Position(3, 0));
        eresult.add(new MarsRover.Position(3, 1));
        eresult.add(new MarsRover.Position(3, 2));
        eresult.add(new MarsRover.Position(2, 2));

        Assert.assertTrue(testPathEquality(aresult, eresult));
    }

    @Test
    public void testExplorePath8() {
        MarsRover.traverseCmd("PLACE", 0, 0);
        MarsRover.traverseCmd("BLOCK", 4, 3);
        MarsRover.traverseCmd("BLOCK", 4, 1);
        MarsRover.traverseCmd("BLOCK", 2, 3);
        Stack<MarsRover.Position> aresult = MarsRover.traverseCmd("EXPLORE", 4, 4);

        Stack<MarsRover.Position> eresult = new Stack<>();
        eresult.add(new MarsRover.Position(0, 0));
        eresult.add(new MarsRover.Position(0, 1));
        eresult.add(new MarsRover.Position(0, 2));
        eresult.add(new MarsRover.Position(0, 3));
        eresult.add(new MarsRover.Position(0, 4));
        eresult.add(new MarsRover.Position(1, 4));
        eresult.add(new MarsRover.Position(2, 4));
        eresult.add(new MarsRover.Position(3, 4));
        eresult.add(new MarsRover.Position(4, 4));

        Assert.assertTrue(testPathEquality(aresult, eresult));
    }

    @Test
    public void testExplorePath9() {
        MarsRover.traverseCmd("PLACE", 4, 3);
        MarsRover.traverseCmd("BLOCK", 4, 1);
        MarsRover.traverseCmd("BLOCK", 2, 3);
        Stack<MarsRover.Position> aresult = MarsRover.traverseCmd("EXPLORE", 0, 0);

        Stack<MarsRover.Position> eresult = new Stack<>();
        eresult.add(new MarsRover.Position(4, 3));
        eresult.add(new MarsRover.Position(3, 3));
        eresult.add(new MarsRover.Position(3, 2));
        eresult.add(new MarsRover.Position(3, 1));
        eresult.add(new MarsRover.Position(3, 0));
        eresult.add(new MarsRover.Position(2, 0));
        eresult.add(new MarsRover.Position(1, 0));
        eresult.add(new MarsRover.Position(0, 0));

        Assert.assertTrue(testPathEquality(aresult, eresult));
    }

    @Test
    public void testExplorePath10() {
        MarsRover.traverseCmd("PLACE", 0, 0);
        MarsRover.traverseCmd("BLOCK", 4, 1);
        MarsRover.traverseCmd("BLOCK", 2, 3);
        Stack<MarsRover.Position> aresult = MarsRover.traverseCmd("EXPLORE", 5, 6);

//        Stack<MarsRover.Position> eresult = new Stack<>();
//        eresult.add(new MarsRover.Position(4, 3));
//        eresult.add(new MarsRover.Position(3, 3));
//        eresult.add(new MarsRover.Position(3, 2));
//        eresult.add(new MarsRover.Position(3, 1));
//        eresult.add(new MarsRover.Position(3, 0));
//        eresult.add(new MarsRover.Position(2, 0));
//        eresult.add(new MarsRover.Position(1, 0));
//        eresult.add(new MarsRover.Position(0, 0));
//
//        Assert.assertTrue(testPathEquality(aresult, eresult));
    }

    @Test
    public void testExplorePath11() {
        MarsRover.traverseCmd("PLACE", 0, 0);
        MarsRover.traverseCmd("BLOCK", 4, 1);
        MarsRover.traverseCmd("BLOCK", 2, 3);
        Stack<MarsRover.Position> aresult = MarsRover.traverseCmd("EXPLORE", 6, 6);

//        Stack<MarsRover.Position> eresult = new Stack<>();
//        eresult.add(new MarsRover.Position(4, 3));
//        eresult.add(new MarsRover.Position(3, 3));
//        eresult.add(new MarsRover.Position(3, 2));
//        eresult.add(new MarsRover.Position(3, 1));
//        eresult.add(new MarsRover.Position(3, 0));
//        eresult.add(new MarsRover.Position(2, 0));
//        eresult.add(new MarsRover.Position(1, 0));
//        eresult.add(new MarsRover.Position(0, 0));
//
//        Assert.assertTrue(testPathEquality(aresult, eresult));
    }

    @Test
    public void testExplorePath12() {
        MarsRover.traverseCmd("PLACE", 0, 0);

        Stack<MarsRover.Position> aresult = MarsRover.traverseCmd("EXPLORE", -1, -1);

//        Stack<MarsRover.Position> eresult = new Stack<>();
//        eresult.add(new MarsRover.Position(4, 3));
//        eresult.add(new MarsRover.Position(3, 3));
//        eresult.add(new MarsRover.Position(3, 2));
//        eresult.add(new MarsRover.Position(3, 1));
//        eresult.add(new MarsRover.Position(3, 0));
//        eresult.add(new MarsRover.Position(2, 0));
//        eresult.add(new MarsRover.Position(1, 0));
//        eresult.add(new MarsRover.Position(0, 0));
//
//        Assert.assertTrue(testPathEquality(aresult, eresult));
    }
}
