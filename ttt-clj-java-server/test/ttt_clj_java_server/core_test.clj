(ns ttt-clj-java-server.core-test
  (:require [clojure.test :refer :all]
            [ttt-clj-java-server.core :refer :all]
            [ttt-clj-java-server.api.server :refer [add-route get-server new-server-builder]])
  (:import com.javawebserver.app.Server))

(deftest build-routes-test
  (testing "it adds routes to ServerBuilder"
    (let [port 9000
          builder (new-server-builder)
          expected-routes ["GET /" "POST /move"]]
      (is (expected-routes (get-routes builder))))))

(deftest build-routes-test
  (testing "it builds app sever"
    (let [port 9000]
      (is (instance? Server (build-server port))))))
