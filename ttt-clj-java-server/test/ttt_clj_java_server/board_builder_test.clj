(ns ttt-clj-java-server.board-builder-test
  (:require [clojure.test :refer :all]
            [ttt-clj-java-server.board-builder :refer :all]))

(deftest add-move-test
  (testing "adds valid move to board"
    (let [board [["_""_""_"]["_""_""_"]["_""_""_"]]
          move 1
          marker "x"
          expected [["x" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]]]
      (is (= expected (add-move board move marker))))))

(deftest updated-position-test
  (testing "it updates the board based on position"
    (let [position "x=1"
          board[["_""_""_"]["_""_""_"]["_""_""_"]]
          expected[["x""_""_"]["_""_""_"]["_""_""_"]]]
      (is (= expected (update-position board position))))))

(deftest fill-board-test
  (testing "it up dates the board based on  positions collection"
    (let [position ["x=1" "o=2"]
          board [["_""_""_"]["_""_""_"]["_""_""_"]]
          expected [["x""o""_"]["_""_""_"]["_""_""_"]]]
      (is (= expected (fill-board  board position))))))

(deftest create-table-data-test 
  (testing "it creates table data on position marker"
    (let [index 0
          marker "_"
          expected "<td><input type=\"radio\" value=\"1\" name=\"openPosition\"></td>"]
      (is (= expected (create-table-data index marker))))))

(deftest build-row-data-test
  (testing "builds required table data based on board"
    (let [board [[["x" "_" "_"]]]
          expected['("<td>x<input type=\"hidden\" value=\"1\" name=\"x\"></td>")
                    '("<td><input type=\"radio\" value=\"2\" name=\"openPosition\"></td>")
                    '("<td><input type=\"radio\" value=\"3\" name=\"openPosition\"></td>")]]
      (is (= expected (build-rows board))))))

(deftest get-board-view-test
  (testing "builds board view based on board"
    (let [board  [['("<td><input type=\"radio\" value=\"1\" name=\"openPosition\"></td>")
                   '("<td><input type=\"radio\" value=\"2\" name=\"openPosition\"></td>")
                   '("<td><input type=\"radio\" value=\"3\" name=\"openPosition\"></td>")]
                  ['("<td><input type=\"radio\" value=\"4\" name=\"openPosition\"></td>")
                   '("<td><input type=\"radio\" value=\"5\" name=\"openPosition\"></td>")
                   '("<td><input type=\"radio\" value=\"6\" name=\"openPosition\"></td>")]
                  ['("<td><input type=\"radio\" value=\"7\" name=\"openPosition\"></td>")
                   '("<td><input type=\"radio\" value=\"8\" name=\"openPosition\"></td>")
                   '("<td><input type=\"radio\" value=\"9\" name=\"openPosition\"></td>")]]
          expected ""]
      (is (= expected (get-board-view board))))))
