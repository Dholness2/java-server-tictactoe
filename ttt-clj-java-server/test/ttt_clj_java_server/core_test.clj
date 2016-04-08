(ns ttt-clj-java-server.core-test
  (:require [clojure.test :refer :all]
            [ttt-clj-java-server.core :refer :all]
            [ttt-clj-java-server.api.server :refer [add-route get-routes get-server new-server-builder]])
  (:import com.javawebserver.app.Server))

(deftest build-routes-test
  (testing "it adds routes to ServerBuilder"
    (let [port 9000
          builder (new-server-builder)
          expected-routes ["GET /" "POST /move"]]
      (is (expected-routes (get-routes builder))))))

(deftest build-routes-test
  (testing "it builds app server"
    (let [port 9510]
      (is (instance? Server (build-server port))))))
