(ns ttt-clj-java-server.core
  (:require [ttt-clj-java-server.responses.new-game-response :refer [new-game]]
            [ttt-clj-java-server.responses.select-move :refer [select-move]]
            [ttt-clj-java-server.api.server :refer [add-route get-server]]))

(defn build-routes[]
 (add-route "GET /" (new-game))
 (add-route "GET /move?" (select-move)))

(defn run-server [port]
  (build-routes)
  (let [server (get-server port)]
    (.run server)))

(defn -main [& args]
  (run-server 9100))
