(ns ttt-clj-java-server.responses.game-response
  (:require [ttt-clj-java-server.api.response-builder :refer [new-response-builder add-status add-header add-body get-response]]
            [ttt-clj-java-server.board-builder :refer [get-board-view current-board add-move]]
            [ttt-clj-java-server.api.request :refer [get-body]]
            [ttt-clj-java-server.helpers.params-parser :refer [get-move]]
            [tic-tac-toe.ai :refer [game-move]]
            [tic-tac-toe.game :refer [winner]]
            [clojure.string :as str])
  (:import com.javawebserver.app.responses.Response))

(def ai-marker "o")
(def player-marker "x")
(def empty-board [["_" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]])

(defn ai-move-to-board [board]
  (let [game {:board board :ai-marker ai-marker :player-marker player-marker}
        new-state (game-move game ai-marker)]
    (new-state :board)))

(defn update-board [params]
  (let [board  (current-board params)]
    (if (winner board)
      board
      (let[player-move (get-move params)
           new-board  (add-move board  player-move player-marker)]
        (if (winner new-board)
          new-board
          (ai-move-to-board new-board))))))

(defn build-response-message [body]
  (let [body-bytes (.getBytes body)
        response (new-response-builder)]
    (add-status response "OK")
    (add-header response "Content-length : " (str (count body-bytes)))
    (add-body response body-bytes)
    response))

(defn emptybody? [request]
  (let [body (get-body request)]
    (or (= nil body)(= body "move=Submit+Move"))))

(defn empty-body-response []
  (let [body (get-board-view empty-board)
        response (build-response-message body)]
    (get-response response)))

(defn partialboard? [request]
  (let [full-request (get-body request)
        x-check (boolean (re-find #"x=" full-request))
        o-check  (boolean  (re-find #"o=" full-request))]
    (or x-check o-check)))

(defn partial-board-response [request]
  (let [current-board (current-board (get-body request))
        body (get-board-view current-board)
        response (build-response-message body)]
    (get-response response)))

(defn moveselected? [request]
  (let [body (get-body request)]
    (.contains body "openPosition")))

(defn move-selected-response [request]
  (let [updated-board (update-board (get-body request))
        body (get-board-view  updated-board)
        response (build-response-message body)]
    (get-response response)))

(defn game []
  (reify Response
    (handleRequest [this request]
      (cond
        (emptybody? request) (empty-body-response)
        (moveselected? request) (move-selected-response request)
        (partialboard? request) (partial-board-response request)
        ))))
