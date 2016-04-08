(ns ttt-clj-java-server.api.server-test
  (:require [clojure.test :refer :all]
            [ttt-clj-java-server.responses.game-response :refer [game]]
            [ttt-clj-java-server.api.server :refer :all])
  (:import com.javawebserver.app.serverBuilders.SimpleServerBuilder)
  (:import com.javawebserver.app.Server))

(deftest new-server-builder-test
  (testing "creates Java SimpleServerBuilder instance"
    (is (instance? SimpleServerBuilder (new-server-builder)))))

(deftest get-server-test
  (testing "returns Java Server instance"
    (let [builder (new-server-builder)]
      (is (instance? Server (get-server 9000 builder))))))

(deftest add-route-test
  (testing "adds routes to ServerBuilder instance"
    (let [builder (new-server-builder)
          response (game)]
      (add-route "GET /" response builder)
      (is (contains? (get-routes builder) "GET /")))))

