(ns ttt-clj-java-server.api.response-builder-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [ttt-clj-java-server.api.response-builder :refer :all])
  (:import com.javawebserver.app.responseBuilders.HttpResponseBuilder))

(defn byte-to-string [bytes-body]
  (apply str (map char bytes-body)))

(deftest new-response-builder-test
  (testing "it builds a instance of the HttpResponseBuilder class"
    (is (instance? HttpResponseBuilder (new-response-builder)))))

(deftest add-status-test
  (testing "it adds status header to response "
    (let [response (new-response-builder)
          status  "OK"]
      (add-status response status)
      (is (= (byte-to-string (get-response response)) (str "HTTP/1.1 200 OK\n"))))))

(deftest add-header-test
  (testing "it adds header to response object"
    (let [response (new-response-builder)
          header "Set-Cookie:"
          header-value "x________"
          crlf "\n"]
      (add-header response header header-value)
      (is (= (byte-to-string (get-response response)) (str header header-value crlf))))))

(deftest add-body-test
  (testing "it added body to response object"
    (let [response (new-response-builder)]
      (add-status response "OK")
      (is (= (byte-to-string (get-response response)))))))
