(ns ttt-clj-java-server.responses.select-move
  (:require [ttt-clj-java-server.api.response-builder :refer :all]
            [ttt-clj-java-server.board-builder :refer :all]
            [ttt-clj-java-server.helpers.params-parser :refer :all]
            [ttt-clj-java-server.api.request :refer :all]
            [tic-tac-toe.ai :refer :all]
            [tic-tac-toe.game :refer :all])
  (:import com.javawebserver.app.responses.Response))

(def ai-marker "o")
(def player-marker "x")

(defn ai-move-to-board [board]
  (let [game {:board board :ai-marker ai-marker :player-marker player-marker}
        new-state (game-move game ai-marker)]
    (new-state :board)))

(defn build-response-message [body]
  (let [body-bytes (.getBytes body)
        response (new-response-builder)]
    (add-status response "OK")
    (add-header response "Content-length : " (str (count body-bytes)))
    (add-body response body-bytes)
    response))

(defn response [params]
  (let [board  (current-board params)]
    (if (winner board)
      board
      (let[player-move (get-move params)
           new-board  (add-move board  player-move player-marker)]
      (ai-move-to-board new-board)))))

(defn select-move []
  (reify Response
    (handleRequest [this request]
      (if (not= nil (get-params request))
        (let [response-board (response (get-params request))
              body (get-board-view (build-rows response-board))
              response (build-response-message body)]
          (get-response response))
        (let [body (str (slurp "view/index.html"))
              response (build-response-message body)]
          (get-response response))))))
