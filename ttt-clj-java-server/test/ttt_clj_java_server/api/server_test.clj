(ns ttt-clj-java-server.api.server-test
  (:require [clojure.test :refer :all]
            [ttt-clj-java-server.responses.game-response :refer [game]]
            [ttt-clj-java-server.api.server :refer :all])
  (:import com.javawebserver.app.Server))

(deftest server-get-server
  (testing "returns java Server object"
    (is (instance? Server (get-server  9000)))))

(deftest server-add-route
  (testing "adds routes"
   (add-route "GET /" (game 9000))
    (let [routes (get-routes)]
    (is (contains? routes "GET /")))))
