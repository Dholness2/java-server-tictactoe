(ns ttt-clj-java-server.core
  (:require [ttt-clj-java-server.responses.game-response :refer [game]]
            [ttt-clj-java-server.api.server :refer [add-route get-server new-server-build]]))

(defn build-routes[port builder]
  (add-route "GET /" (game) builder)
  (add-route "POST /move" (game) builder)
  (add-route "GET /move" (game) builder))

(defn build-server [port]
  (let [builder (new-server-build)
        server (get-server port builder)]
    (build-routes port builder)
    server))

(defn -main [& args]
  (.run (build-server 9300)))
