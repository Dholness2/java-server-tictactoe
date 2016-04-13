(ns ttt-clj-java-server.responses.game-response-test
  (:require [clojure.test :refer :all]
            [ttt-clj-java-server.responses.game-response :refer :all]
            [ttt-clj-java-server.api.response-builder :refer :all]
            [ttt-clj-java-server.board-builder :refer :all]
            [ttt-clj-java-server.api.request :refer :all])
  (:import com.javawebserver.app.responses.Response))

(defn byte-to-string [bytes-body]
  (apply str (map char bytes-body)))

(defn build-request [params]
  (let[request (new-request)]
    (set-body request params)
    request))

(deftest ai-move-to-board-test
  (testing "it updates the board with the AI's move"
    (let [board [["x" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]]
          expected-board [["x" "_" "_"] ["_" "o" "_"] ["_" "_" "_"]]]
      (is (= expected-board (ai-move-to-board board))))))

(deftest update-board-test
  (testing "returns a new board with player's and AI's selections"
    (let [params "x=1&o=2&openPosition=3&move=submit"
          expected [["x" "o" "x"] ["_" "o" "_"] ["_" "_" "_"]]]
      (is (= expected (update-board params))))))

(deftest update-board-test-win-state
  (testing "returns  current board during win state"
    (let [params "x=1&x=2&x=3&o=5&o=8&openPosition=3&move=submit"
          expected [["x" "x" "x"] ["_" "o" "_"] ["_" "o" "_"]]]
      (is (= expected (update-board params))))))

(deftest update-board-test-player-move-win
  (testing "returns updated board if player submits winning move"
    (let [params "x=1&x=3&o=5&o=8&openPosition=2&move=submit"
          expected [["x" "x" "x"] ["_" "o" "_"] ["_" "o" "_"]]]
      (is (= expected (update-board params))))))

(deftest build-response-message-test
  (testing "it returns response object with correct headers and body"
    (let [body "hello world"
          response (build-response-message body)
          expected "HTTP/1.1 200 OK\nContent-length : 11\n\nhello world"]
      (is (= expected (byte-to-string (get-response response)))))))

(deftest empty-body-test-true-state
  (testing "checks request body for content"
    (let [request (new-request)]
      (is (= true (empty-body? request))))))

(deftest empty-body-test-false-state
  (testing "checks request body for content"
    (let [request (build-request "x=1&o=2&openPosition=3&move=submit")]
      (is (= false (empty-body? request))))))

(deftest empty-body-response-test
  (testing "returns empty board view response"
    (let [body (get-board-view [["_" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]])
          response (build-response-message body)
          expected-response  (byte-to-string (get-response response))
          test-response (byte-to-string (empty-body-response))]
      (is (= expected-response test-response)))))

(deftest partial-board-test-true-state
  (testing "it returns true if the board has no current moves"
    (let  [request (build-request "x=1&o=2&openPosition=3&move=submit")]
     (is (= true (partial-board? request))))))

(deftest partial-board-test-false-state
  (testing "it returns true if the board has no current moves"
    (let  [request (build-request "openPosition=3&move=submit")]
     (is (= false (partial-board? request))))))


(deftest partial-board-response-test
  (testing "returns current board if no move selection is provided"
    (let [params "x=1&move=submit"
          request (build-request params)
          body (get-board-view [["x" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]])
          response (build-response-message body)
          expected-response (byte-to-string (get-response response))
          test-response (byte-to-string (partial-board-response request))]
      (is (= expected-response test-response)))))

(deftest move-selection-test-false-state
  (testing "it returns true if open position selection param is empty"
    (let [request (build-request "")]
      (is (= false (move-selected? request))))))

(deftest move-selected-test-true-state
  (testing "it returns true if a open position is empty"
    (let [request (build-request "x=1&o=2&openPosition=3&move=submit")]
      (is (= true (move-selected? request))))))

(deftest move-selected-test-true-state
  (testing "it returns true if a open position is empty"
    (let [request (build-request "x=1&o=2&openPosition=3&move=submit")]
      (is (= true (move-selected? request))))))

(deftest move-selected-response-test
  (testing "returns updated board view based on player's move selection"
    (let [params "x=1&o=2&openPosition=3&move=submit"
          request (build-request params)
          body (get-board-view [["x" "o" "x"] ["_" "o" "_"] ["_" "_" "_"]])
          response (build-response-message body)
          expected-response (byte-to-string (get-response response))
          test-response (byte-to-string (move-selected-response request))]
      (is (= expected-response test-response)))))

(deftest handle-request-test-partial-state
  (testing "it returns partial-board if no move is selected"
    (let [request (build-request "x=1&o=2&move=submit")
          game-response (game)
          expected-response (byte-to-string (partial-board-response request))
          test-response (byte-to-string (.handleRequest game-response request))]
      (is (= expected-response test-response)))))

(deftest handle-request-test-empty-state
  (testing "it returns empty-board-response if body is empty"
    (let [request (new-request)
          game-response (game)
          expected-response (byte-to-string (empty-body-response))
          test-response (byte-to-string (.handleRequest game-response request))]
      (is (= expected-response test-response)))))

(deftest handle-request-test-move-selected
  (testing "it returns updated board if move is selected"
    (let [request (build-request "x=1&o=2&openPosition=3&move=submit")
          game-response (game)
          expected-response (byte-to-string (move-selected-response request))
          test-response     (byte-to-string (.handleRequest game-response request))]
      (is (= expected-response test-response)))))
