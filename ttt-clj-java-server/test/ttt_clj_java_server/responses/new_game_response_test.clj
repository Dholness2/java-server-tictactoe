(ns ttt-clj-java-server.responses.new-game-response-test
  (:require [clojure.test :refer :all]
            [ttt-clj-java-server.responses.new-game-response :refer :all]
            [ttt-clj-java-server.api.response-builder :refer :all])
  (:import com.javawebserver.app.Request)
  (:import com.javawebserver.app.responses.Response))

(defn build-request []
  (let[request (Request.)]
    (.setMessage request "GET /new_game HTTP/1.1")
    request))

(defn byte-to-string [bytes-body]
  (apply str (map char bytes-body)))

(deftest new-game-response-new-game
  (testing "implements Response interface"
    (is (instance? Response (new-game)))))

(deftest handle-request-test
  (testing "it returns a new game response"
    (let[game-response (new-game)
         response (.handleRequest game-response (build-request))
         status "HTTP/1.1 200 OK\n"
         content-length "Content-length : 833\n\n"
         body (slurp "views/index.html")
         expected-response (str status content-length body)]
      (is (= expected-response (byte-to-string response))))))

(deftest get-response-message-test
  (testing "it assemble all response elements"
    (let [body "hello world"
          response (build-response-message (.getBytes body))
          status "HTTP/1.1 200 OK\n"
          content-length "Content-length : 11\n\n"
          expected-response (str status content-length body)]
      (is (= expected-response (byte-to-string (get-response response)))))))
