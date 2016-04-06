(ns ttt-clj-java-server.helpers.params-parser-test
  (:require [clojure.test :refer :all]
            [ttt-clj-java-server.helpers.params-parser :refer :all]))

(deftest get-move-test
  (testing "it parse params for move selection"
    (let[params "x=0&o=1&openPosition=2&move=submit"
         expected 2]
      (is (= expected (get-move params))))))

(deftest get-board-positions-test
  (testing "it parse params for filled board positions"
    (let [params "x=0&o=1&openPosition=2&move=submit"
          expected ["x=0" "o=1"]]
      (is (= expected (get-board-positions params))))))

(deftest parse-params-test
  (testing "it parses params into a vector"
    (let[params "x=0&o=1&openPosition=2&move=Submit"
         expected ["x=0" "o=1" "openPosition=2" "move=Submit"]]
      (is (= expected (parse-params params))))))
