(ns ttt-clj-java-server.board-builder-test
  (:require [clojure.test :refer :all]
            [ttt-clj-java-server.board-builder :refer :all]
            [tic-tac-toe.game :refer :all]
            [clojure.string :as str]))

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

(deftest winner-header-test
  (testing "it builds winner header"
   (let [board [["x""x""x"]["o""_""o"]["_""_""_"]]
         winner (winner board)
         expected-header (str "<h2> Winner: " winner "!</h2>")]
     (is (= expected-header (winner-header board))))))

(deftest winner-header-test-false-state
  (testing "it builds winner header"
   (let [board [["x""_""x"]["o""_""o"]["_""_""_"]]
         winner (winner board)
         expected-header "<h2> Select move</h2>"]
     (is (= expected-header (winner-header board))))))

(deftest form-submit-test
  (testing "it removes submit button"
   (let [board [["x""x""x"]["o""_""o"]["_""_""_"]]
         expected   ""]
     (is (= expected (form-submit board))))))

(deftest form-submit-test-false-state
  (testing "it adds submit button"
    (let [board [["x""_""x"]["o""_""o"]["_""_""_"]]
          expected "<input type=\"submit\" value=\"Submit Move\" name=\"move\">"]
      (is (= expected (form-submit board))))))

(deftest get-board-view-test
  (testing "it builds board view based on board"
    (let [board [["_""_""_"]["_""_""_"]["_""_""_"]]
          expected(str "<html>"
                     "<h1>Unbeatable Tic-Tac-Toe</h1>"
                     "<h2> Select move</h2>"
                     "<body>"
                      "<form action=\"/move\" method=\"POST\">"
                        "<table>"
                          "<tr>"
                            "<td><input type=\"radio\" value=\"1\" name=\"openPosition\"></td>"
                            "<td><input type=\"radio\" value=\"2\" name=\"openPosition\"></td>"
                            " <td><input type=\"radio\" value=\"3\" name=\"openPosition\"></td>"
                        "</tr>"
                        "<tr>"
                           "<td><input type=\"radio\" value=\"4\" name=\"openPosition\"></td>"
                           "<td><input type=\"radio\" value=\"5\" name=\"openPosition\"></td>"
                           "<td><input type=\"radio\" value=\"6\" name=\"openPosition\"></td>"
                        "</tr>"
                        "<tr>"
                           "<td><input type=\"radio\" value=\"7\" name=\"openPosition\"></td>"
                           "<td><input type=\"radio\" value=\"8\" name=\"openPosition\"></td>"
                           "<td><input type=\"radio\" value=\"9\" name=\"openPosition\"></td>"
                        "</tr>"
                        "</table><input type=\"submit\" value=\"Submit Move\" name=\"move\">"
                      "</form><p><a href=/>New Game</a></p>"
                    "</body>"
                   "</html>")]
     (is (= expected (get-board-view board))))))
