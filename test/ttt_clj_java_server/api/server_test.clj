(ns ttt-clj-java-server.api.server-test
  (:require [clojure.test :refer :all]
            [ttt-clj-java-server.responses.game-response :refer [game]]
            [ttt-clj-java-server.api.server :refer [server-on? server-off new-server-build get-server add-route get-routes]])
  (:import com.javawebserver.app.builds.BasicBuild)
  (:import com.javawebserver.app.serverBuilders.SimpleServerBuilder)
  (:import com.javawebserver.app.Server))

(deftest new-server-builder-test
  (testing "it creates Java SimpleServerBuilder instance"
    (is (instance? BasicBuild (new-server-build)))))

(deftest get-server-test
  (testing "it returns Java Server instance"
    (let [build (new-server-build)]
      (is (instance? Server (get-server 9001 build))))))

(deftest add-route-test
  (testing "it adds routes to ServerBuild instance"
    (let [build (new-server-build)
          response (game)]
      (add-route "GET /" response build)
      (is (contains? (get-routes build) "GET /")))))

(deftest server-off-test
  (testing "it turns server off"
    (let [build (new-server-build)
          server (get-server 8989 build)]
      (server-off server)
      (is (false? (server-on? server))))))
