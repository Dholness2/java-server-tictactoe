(ns ttt-clj-java-server.api.server-test
  (:require [clojure.test :refer :all]
            [ttt-clj-java-server.responses.game-response :refer [game]]
            [ttt-clj-java-server.api.server :refer [serveron? server-off new-server-builder get-server add-route get-routes]])
  (:import com.javawebserver.app.serverBuilders.SimpleServerBuilder)
  (:import com.javawebserver.app.Server))

(deftest new-server-builder-test
  (testing "it creates Java SimpleServerBuilder instance"
    (is (instance? SimpleServerBuilder (new-server-builder)))))

(deftest get-server-test
  (testing "it returns Java Server instance"
    (let [builder (new-server-builder)]
      (is (instance? Server (get-server 9001 builder))))))

(deftest add-route-test
  (testing "it adds routes to ServerBuilder instance"
    (let [builder (new-server-builder)
          response (game)]
      (add-route "GET /" response builder)
      (is (contains? (get-routes builder) "GET /")))))

(deftest server-off-test
  (testing "it turns server off"
    (let [builder (new-server-builder)
          server (get-server 8989 builder)]
      (server-off server)
      (is (false? (serveron? server))))))
