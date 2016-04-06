(ns ttt-clj-java-server.responses.select-move-test
  (:require [clojure.test :refer :all]
            [ttt-clj-java-server.responses.select-move :refer :all]
            [ttt-clj-java-server.api.response-builder :refer :all])
  (:import com.javawebserver.app.Request)
  (:import com.javawebserver.app.responses.Response))

(defn byte-to-string [bytes-body]
  (apply str (map char bytes-body)))

(defn build-request []
  (let[request (Request.)]
    (.setMessage request "GET /next_move?openPosition=0&move=Submit HTTP/1.1")
    request))

(deftest ai-move-to-board-test
  (testing "it updates the board with the Ai's move  "
  (let [board [["x" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]]
        expected-board [["x" "_" "_"] ["_" "o" "_"] ["_" "_" "_"]]]
    (is (= expected-board (ai-move-to-board board)))))

  (deftest build-response-message-test
    (testing "it resturns response object with correct headers and body"
      (let [body "hello world"
            response (build-response-message body)
            expected ""]
        (is (= expected (byte-to-string get-response response))))))
     
;;(deftest response-test
 ;; (testing "returns the current state of the board"
   ;; (let[params "x=1&x=2&o=4&openpositon=2&move=submit"
     ;;    expected [["x" "x" "o"] ["o" "_" "_"] ["_" "_" "_"]]]
     ;; (is (= expected (response params))))))
