(ns ttt-clj-java-server.core
  (:require [ttt-clj-java-server.responses.game-response :refer [game]]
            [ttt-clj-java-server.api.server :refer [add-route get-server new-server-builder]]))

(defn build-routes[port builder]
  (add-route "GET /" (game) builder)
  (add-route "POST /move" (game) builder))

(defn run-server [port]
  (let [builder (new-server-builder)
        server (get-server port builder)]
    (build-routes port builder)
    (.run server)))

(defn -main [& args]
  (run-server 9000))
