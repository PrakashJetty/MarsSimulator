import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;


//
public class MarsRover {

    private static final Scanner scanner = new Scanner(System.in);

    public static class Position {
        private int x;
        private int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" +
                    "x=" + x +
                    ", y=" + y +
                    ')';
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Position)) return false;
            Position position = (Position) o;
            return x == position.x &&
                    y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private static Queue<Position> pathMap = new LinkedList<>();
    private static Set<Position> blockSet = new HashSet<>();

//    static {
//        blockSet.addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6).stream().map(i -> {
//            return new Position(i, 6);
//        }).collect(Collectors.toSet()));
//        blockSet.addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6).stream().map(i -> {
//            return new Position(6, i);
//        }).collect(Collectors.toSet()));
//        blockSet.addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6).stream().map(i -> {
//            return new Position(i, -1);
//        }).collect(Collectors.toSet()));
//        blockSet.addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6).stream().map(i -> {
//            return new Position(-1, i);
//        }).collect(Collectors.toSet()));
//        blockSet.addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6).stream().map(i -> {
//            return new Position(-1, -i);
//        }).collect(Collectors.toSet()));
//        blockSet.addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6).stream().map(i -> {
//            return new Position(-i, -1);
//        }).collect(Collectors.toSet()));
//    }


    public MarsRover() {
    }


    // gets next coordinate in normal movement
    private static int getNextTreadCoordinate(Position rposition, Position cposition, int coordinate, boolean prevBlocked) {

        if (rposition.getX() > cposition.getX() && rposition.getY() > cposition.getY()) {
            return prevBlocked ? (coordinate == 1 ? 2 : (coordinate == 2 ? 1 : 2)) : coordinate;
        } else if (rposition.getX() > cposition.getX() && rposition.getY() == cposition.getY()) {
            return prevBlocked ? (coordinate == 1 ? 2 : 1) : 1;
        } else if (rposition.getY() > cposition.getY() && rposition.getX() == cposition.getX()) {
            return prevBlocked ? (coordinate == 2 ? 1 : 2) : 2;
        } else if (rposition.getX() > cposition.getX() && rposition.getY() < cposition.getY()) {
            return prevBlocked ? (coordinate == 1 ? -2 : 1) : 1;
        } else if (rposition.getY() < cposition.getY() && rposition.getX() == cposition.getX()) {
            return prevBlocked ? (coordinate == -2 ? 1 : -2) : -2;
        } else if (rposition.getY() < cposition.getY() && rposition.getX() < cposition.getX()) {
            return prevBlocked ? (coordinate == -1 ? -2 : (coordinate == 1 ? -2 : -1)) : coordinate;
        } else if (rposition.getY() == cposition.getY() && rposition.getX() < cposition.getX()) {
            return prevBlocked ? (coordinate == -1 ? 2 : -1) : -1;
        } else if (rposition.getY() > cposition.getY() && rposition.getX() < cposition.getX()) {
            return prevBlocked ? (coordinate == 2 ? -1 : 2) : 2;
        } else {
            return 0;
        }
    }


    // move unit distance
    private static void tread(Position currentPosition, int coordinate) {
        switch (Math.abs(coordinate)) {
            case 1:
//                if (currentPosition.getX() + coordinate <= 5)
                currentPosition.setX(currentPosition.getX() + coordinate);
                break;

            case 2:
//                if (currentPosition.getY() + (coordinate / 2) <= 5)
                currentPosition.setY(currentPosition.getY() + (coordinate / 2));
                break;
        }
    }


    // choses next optimum direction to tread based on previously visited coordinates
    // this is used when we encounter blocks only
    private static int getNextDirectionToTreadBlockedState(Position rposition, Position cposition, Stack<Integer> coordinateStack) {
        int coordinate = coordinateStack.peek();
        if (coordinate == 1) {
            if (rposition.getX() > cposition.getX() && rposition.getY() > cposition.getY()) {
                if (coordinateStack.stream().noneMatch(c -> c == 2)) {
                    return 2;
                } else if (coordinateStack.stream().noneMatch(c -> c == -2)) {
                    return -2;
                } else {
                    return -1;
                }
            } else if (rposition.getX() > cposition.getX() && rposition.getY() == cposition.getY()) {
                if (coordinateStack.stream().noneMatch(c -> c == 2)) {
                    return 2;
                } else if (coordinateStack.stream().noneMatch(c -> c == -2)) {
                    return -2;
                } else {
                    return -1;
                }

            } else if (rposition.getY() > cposition.getY() && rposition.getX() == cposition.getX()) {
                if (coordinateStack.stream().noneMatch(c -> c == 2)) {
                    return 2;
                } else if (coordinateStack.stream().noneMatch(c -> c == -2)) {
                    return -2;
                } else {
                    return -1;
                }
            } else if (rposition.getX() > cposition.getX() && rposition.getY() < cposition.getY()) {
                if (coordinateStack.stream().noneMatch(c -> c == -2)) {
                    return -2;
                } else if (coordinateStack.stream().noneMatch(c -> c == 2)) {
                    return 2;
                } else {
                    return -1;
                }

            } else if (rposition.getX() == cposition.getX() && rposition.getY() < cposition.getY()) {
                if (coordinateStack.stream().noneMatch(c -> c == -2)) {
                    return -2;
                } else if (coordinateStack.stream().noneMatch(c -> c == 2)) {
                    return 2;
                } else {
                    return -1;
                }
            } else if (rposition.getX() < cposition.getX() && rposition.getY() < cposition.getY()) {
                if (coordinateStack.stream().noneMatch(c -> c == -2)) {
                    return -2;
                } else if (coordinateStack.stream().noneMatch(c -> c == 2)) {
                    return 2;
                } else {
                    return -1;
                }
            } else if (rposition.getX() < cposition.getX() && rposition.getY() > cposition.getY()) {
                if (coordinateStack.stream().noneMatch(c -> c == 2)) {
                    return 2;
                } else if (coordinateStack.stream().noneMatch(c -> c == -2)) {
                    return -2;
                } else {
                    return -1;
                }
            } else {
                if (coordinateStack.stream().noneMatch(c -> c == 2)) {
                    return 2;
                } else if (coordinateStack.stream().noneMatch(c -> c == -2)) {
                    return -2;
                } else {
                    return -1;
                }
            }
        } else if (coordinate == -1) {
            if (rposition.getX() < cposition.getX() && rposition.getY() > cposition.getY()) {
                if (coordinateStack.stream().noneMatch(c -> c == 2)) {
                    return 2;
                } else if (coordinateStack.stream().noneMatch(c -> c == -2)) {
                    return -2;
                } else {
                    return 1;
                }
            } else if (rposition.getX() < cposition.getX() && rposition.getY() == cposition.getY()) {
                if (coordinateStack.stream().noneMatch(c -> c == 2)) {
                    return 2;
                } else if (coordinateStack.stream().noneMatch(c -> c == -2)) {
                    return -2;
                } else {
                    return 1;
                }
            } else if (rposition.getY() < cposition.getY() && rposition.getX() < cposition.getX()) {
                if (coordinateStack.stream().noneMatch(c -> c == -2)) {
                    return -2;
                } else if (coordinateStack.stream().noneMatch(c -> c == 2)) {
                    return 2;
                } else {
                    return 1;
                }
            } else if (rposition.getY() < cposition.getY() && rposition.getX() == cposition.getX()) {
                if (coordinateStack.stream().noneMatch(c -> c == -2)) {
                    return -2;
                } else if (coordinateStack.stream().noneMatch(c -> c == 2)) {
                    return 2;
                } else {
                    return 1;
                }
            } else if (rposition.getY() > cposition.getY() && rposition.getX() == cposition.getX()) {
                if (coordinateStack.stream().noneMatch(c -> c == 2)) {
                    return 2;
                } else if (coordinateStack.stream().noneMatch(c -> c == -2)) {
                    return -2;
                } else {
                    return 1;
                }
            } else if (rposition.getY() > cposition.getY() && rposition.getX() > cposition.getX()) {
                if (coordinateStack.stream().noneMatch(c -> c == 2)) {
                    return 2;
                } else if (coordinateStack.stream().noneMatch(c -> c == -2)) {
                    return -2;
                } else {
                    return 1;
                }
            } else if (rposition.getY() < cposition.getY() && rposition.getX() > cposition.getX()) {
                if (coordinateStack.stream().noneMatch(c -> c == -2)) {
                    return -2;
                } else if (coordinateStack.stream().noneMatch(c -> c == 2)) {
                    return 2;
                } else {
                    return 1;
                }
            } else {
                if (coordinateStack.stream().noneMatch(c -> c == 2)) {
                    return 2;
                } else if (coordinateStack.stream().noneMatch(c -> c == -2)) {
                    return -2;
                } else {
                    return 1;
                }
            }
        } else if (coordinate == 2) {
            if (rposition.getX() > cposition.getX() && rposition.getY() > cposition.getY()) {
                if (coordinateStack.stream().noneMatch(c -> c == 1)) {
                    return 1;
                } else if (coordinateStack.stream().noneMatch(c -> c == -1)) {
                    return -1;
                } else {
                    return -2;
                }
            } else if (rposition.getX() > cposition.getX() && rposition.getY() == cposition.getY()) {
                if (coordinateStack.stream().noneMatch(c -> c == 1)) {
                    return 1;
                } else if (coordinateStack.stream().noneMatch(c -> c == -1)) {
                    return -1;
                } else {
                    return -2;
                }
            } else if (rposition.getY() > cposition.getY() && rposition.getX() == cposition.getX()) {
                if (coordinateStack.stream().noneMatch(c -> c == 1)) {
                    return 1;
                } else if (coordinateStack.stream().noneMatch(c -> c == -1)) {
                    return -1;
                } else {
                    return -2;
                }
            } else if (rposition.getX() < cposition.getX() && rposition.getY() > cposition.getY()) {
                if (coordinateStack.stream().noneMatch(c -> c == -1)) {
                    return -1;
                } else if (coordinateStack.stream().noneMatch(c -> c == 1)) {
                    return 1;
                } else {
                    return -2;
                }
            } else if (rposition.getX() < cposition.getX() && rposition.getY() == cposition.getY()) {
                if (coordinateStack.stream().noneMatch(c -> c == -1)) {
                    return -1;
                } else if (coordinateStack.stream().noneMatch(c -> c == 1)) {
                    return 1;
                } else {
                    return -2;
                }
            } else if (rposition.getX() < cposition.getX() && rposition.getY() < cposition.getY()) {
                if (coordinateStack.stream().noneMatch(c -> c == -1)) {
                    return -1;
                } else if (coordinateStack.stream().noneMatch(c -> c == 1)) {
                    return 1;
                } else {
                    return -2;
                }
            } else if (rposition.getX() > cposition.getX() && rposition.getY() < cposition.getY()) {
                if (coordinateStack.stream().noneMatch(c -> c == 1)) {
                    return 1;
                } else if (coordinateStack.stream().noneMatch(c -> c == -1)) {
                    return -1;
                } else {
                    return -2;
                }
            } else {
                if (coordinateStack.stream().noneMatch(c -> c == 1)) {
                    return 1;
                } else if (coordinateStack.stream().noneMatch(c -> c == -1)) {
                    return -1;
                } else {
                    return -2;
                }
            }
        } else {
            if (rposition.getX() < cposition.getX() && rposition.getY() < cposition.getY()) {
                if (coordinateStack.stream().noneMatch(c -> c == -1)) {
                    return -1;
                } else if (coordinateStack.stream().noneMatch(c -> c == 1)) {
                    return 1;
                } else {
                    return 2;
                }
            } else if (rposition.getX() < cposition.getX() && rposition.getY() == cposition.getY()) {
                if (coordinateStack.stream().noneMatch(c -> c == -1)) {
                    return -1;
                } else if (coordinateStack.stream().noneMatch(c -> c == 1)) {
                    return 1;
                } else {
                    return 2;
                }
            } else if (cposition.getX() > rposition.getX() && rposition.getY() == cposition.getY()) {
                if (coordinateStack.stream().noneMatch(c -> c == 1)) {
                    return 1;
                } else if (coordinateStack.stream().noneMatch(c -> c == -1)) {
                    return -1;
                } else {
                    return 2;
                }
            } else if (rposition.getY() < cposition.getY() && rposition.getX() == cposition.getX()) {
                if (coordinateStack.stream().noneMatch(c -> c == -1)) {
                    return -1;
                } else if (coordinateStack.stream().noneMatch(c -> c == 1)) {
                    return 1;
                } else {
                    return 2;
                }
            } else if (cposition.getX() > rposition.getX() && rposition.getY() < cposition.getY()) {
                if (coordinateStack.stream().noneMatch(c -> c == 1)) {
                    return 1;
                } else if (coordinateStack.stream().noneMatch(c -> c == -1)) {
                    return -1;
                } else {
                    return 2;
                }
            } else if (cposition.getX() > rposition.getX() && rposition.getY() > cposition.getY()) {
                if (coordinateStack.stream().noneMatch(c -> c == 1)) {
                    return 1;
                } else if (coordinateStack.stream().noneMatch(c -> c == -1)) {
                    return -1;
                } else {
                    return 2;
                }
            } else if (cposition.getX() < rposition.getX() && rposition.getY() < cposition.getY()) {
                if (coordinateStack.stream().noneMatch(c -> c == 1)) {
                    return 1;
                } else if (coordinateStack.stream().noneMatch(c -> c == -1)) {
                    return -1;
                } else {
                    return 2;
                }
            } else {
                if (coordinateStack.stream().noneMatch(c -> c == 1)) {
                    return 1;
                } else if (coordinateStack.stream().noneMatch(c -> c == -1)) {
                    return -1;
                } else {
                    return 2;
                }
            }
        }
    }


    // To Handle Move when we encounter blocks, we tread all posssible optimum unit path from current position


    private static Stack<Position> clearBlockNtreadToNextCorrectPosition(Position rposition,
                                                                         Stack<Position> currentResult,
                                                                         Set<Position> cblockedSet,
                                                                         int cordinate, Position currentPosition, boolean fromBlockState, Stack<Integer> coordinateStack, int blockStat) {
        Position cPosition = currentResult.peek();
        currentPosition = new Position(cPosition.getX(), cPosition.getY());
        int ncoordinate = fromBlockState ? getNextDirectionToTreadBlockedState(rposition, currentPosition, coordinateStack) : getNextTreadCoordinate(rposition, currentPosition, cordinate, blockStat >= 1);// if previous was Y current should be X or -Y shoulbe be -X
        tread(currentPosition, ncoordinate);
        if (blockSet.contains(currentPosition) || cblockedSet.contains(currentPosition)) {
            ++blockStat;
            if (!fromBlockState) {
                if (coordinateStack == null)
                    coordinateStack = new Stack<>();

            }
            coordinateStack.add(-cordinate);
            coordinateStack.add(ncoordinate);


            if (fromBlockState && currentResult.size() > 1 && coordinateStack.size() >= 2) {
                if (coordinateStack.size() > 3) {
                    return new Stack<>();
                } else {
                    Position pos1 = currentResult.pop();
                    Position pos2 = currentResult.peek();
                    coordinateStack.clear();
                    if (pos1.getY() > pos2.getY()) {

                        coordinateStack.add(2);
                    } else if (pos1.getY() < pos2.getY()) {
                        coordinateStack.add(-2);
                    } else if (pos1.getX() < pos2.getX()) {
                        coordinateStack.add(-1);
                    } else if (pos1.getX() > pos2.getX()) {
                        coordinateStack.add(1);

                    }
                }
            }

            return clearBlockNtreadToNextCorrectPosition(rposition, currentResult, cblockedSet, ncoordinate, currentPosition, true, coordinateStack, blockStat); // if previous was Y current should be +X
        } else if (currentPosition.equals(rposition)) {
            currentResult.add(new Position(currentPosition.getX(), currentPosition.getY()));
            print(currentResult);
            System.out.println("==============");
            return currentResult;
        } else {
            coordinateStack.clear();
            --blockStat;
            currentResult.add(new Position(currentPosition.getX(), currentPosition.getY()));
            return clearBlockNtreadToNextCorrectPosition(rposition, currentResult, cblockedSet, ncoordinate, currentPosition, false, coordinateStack, blockStat); // if previous was Y current should be +X

        }


    }


    private static void print(Stack<Position> result) {
        result.forEach(p -> System.out.print(p));
    }

    public static Stack<Position> explore(Position rposition, Stack<Position> currentResult, Set<Position> cblockedSet, int cordinate) {
        Position cposition = currentResult.peek();
        Position currentPosition = new Position(cposition.getX(), cposition.getY());
        cordinate = getNextTreadCoordinate(rposition, currentPosition, cordinate, false);
        tread(currentPosition, cordinate);
        if (blockSet.contains(currentPosition) || cblockedSet.contains(currentPosition)) {
            Stack<Integer> coordinateStack = new Stack<>();
            coordinateStack.add(cordinate);
            return clearBlockNtreadToNextCorrectPosition(rposition, currentResult, cblockedSet, cordinate, currentPosition, true, coordinateStack, 1);
        } else if (currentPosition.equals(rposition)) {
            currentResult.add(new Position(currentPosition.getX(), currentPosition.getY()));
            print(currentResult);
            System.out.println("==============");
            return currentResult;
        } else {
            currentResult.add(new Position(currentPosition.getX(), currentPosition.getY()));
            return explore(rposition, currentResult, cblockedSet, cordinate);
        }
    }


    public static Stack<Position> traverseCmd(String cmd, int x, int y) {

        Position position = new Position(x, y);

        switch (cmd) {
            case "BLOCK":
                blockSet.add(position);
                break;
            case "PLACE":
                // blockSet.clear();
                pathMap.clear();
                pathMap.add(position);
                break;
            case "REPORT":
                System.out.println(pathMap.peek().toString());
                scanner.close();
                System.exit(0);
            case "EXPLORE":
                Stack<Position> cresult = new Stack<>();
                Position cPosition = pathMap.peek();
                cresult.add(cPosition);
                Stack<Position> path1 = explore(new Position(x, y), cresult, new HashSet<>(), position.getX() > cPosition.getX() ? 1 : -1);
                cresult = new Stack<>();
                cresult.add(cPosition);
                Stack<Position> path2 = explore(new Position(x, y), cresult, new HashSet<>(), position.getY() > cPosition.getY() ? 2 : -2);
                System.out.println("======= RESULT =======");
                if (path1.size() == 0 && path2.size() > 0) {
                    print(path2);
                    return path2;
                } else if (path2.size() == 0 && path1.size() > 0) {
                    print(path1);
                    return path1;
                } else {
                    if (path1.size() > path2.size()) {
                        print(path2);
                        return path2;
                    } else {
                        print(path1);
                        return path1;
                    }
                }

        }
        return null;
    }


    public static void main(String[] args) throws IOException {

        blockSet.addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6).stream().map(i -> {
            return new Position(i, 6);
        }).collect(Collectors.toSet()));
        blockSet.addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6).stream().map(i -> {
            return new Position(6, i);
        }).collect(Collectors.toSet()));
        do {
            String[] cmdItems = scanner.nextLine().split(" ");
            String[] cords = !cmdItems[0].equals("REPORT") ? cmdItems[1].split(",") : new String[]{"0", "0"};
            traverseCmd(cmdItems[0], Integer.parseInt(cords[0]), Integer.parseInt(cords[1]));
        } while (true);
    }


    public static Set<Position> getBlockSet() {
        return blockSet;
    }

    public static void setBlockSet(Set<Position> blockSet) {
        MarsRover.blockSet = blockSet;
    }
}
