(ns ttt-clj-java-server.api.request-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [ttt-clj-java-server.api.request :refer :all])
  (:import com.javawebserver.app.Request))

(deftest new-request-test
  (testing "it builds instance of Request object"
    (is (instance? Request (new-request)))))

(deftest get-Route-test
  (testing "it return request route"
    (let [request (Request.)]
      (.setMessage request "GET /next_move?openPosition=0&move=Submit HTTP/1.1")
      (is (= (str "/next_move?") (get-route request))))))

(deftest get-Request-test
  (testing "it returns request method and path"
    (let [request (Request.)]
      (.setMessage request "GET /next_move?openPosition=0&move=Submit HTTP/1.1")
      (is (= (str "GET /next_move?") (get-request request))))))

(deftest get-Headers-test
  (testing "it returns request body"
    (let [request (Request.)]
      (.setHeaders request "Content-length: 123 \n")
      (is (= (str "Content-length: 123 \n") (get-headers request))))))

(deftest get-body-test
  (testing "it returns request body"
    (let [request (Request.)]
      (.setBody request "hello world")
      (is (= (str "hello world") (get-body request))))))

(deftest get-params-test
  (testing "it returns request params "
    (let [request (Request.)]
      (.setMessage request "GET /next_move?openPosition=0&move=Submit HTTP/1.1")
      (is (= (str "openPosition=0&move=Submit") (get-params request))))))
