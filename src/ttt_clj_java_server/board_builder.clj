(ns ttt-clj-java-server.board-builder
  (:require [clojure.string :as str]
            [ttt-clj-java-server.helpers.params-parser :refer [get-board-positions parse-move]]
            [tic-tac-toe.board :refer [moveopen? matrix-convrt move create-empty-board]]
            [tic-tac-toe.game :refer [winner]]))

(defn add-move [board move-selection marker]
  (if (moveopen? board move-selection)
    (let [matrix-position (matrix-convrt move-selection 3)]
      (move matrix-position marker board))))

(defn update-position [board position]
  (let[selection (parse-move position)
       marker (first selection)
       move-selection (read-string (last selection))]
    (add-move board move-selection marker)))

(defn fill-board  [board taken-positions]
  (reduce update-position board taken-positions))

(defn current-board [params]
  (let [positions (get-board-positions params)
        board (create-empty-board 3)
        current-board (fill-board board  positions)]
    current-board))

(defn create-table-data [index marker]
  (let [position (inc index)]
    (if (= marker "_")
      (str "<td class=\"square" index "\"><input type=\"radio\" value=\"" position "\" name=\"openPosition\"></td>")
      (str "<td class=\"square" index "\"><a class=\"move\">" marker "</a><input type=\"hidden\" value=\"" position "\" name="\" marker "\"></td>"))))

(defn build-rows [board]
  (let [board-size (count board)
        board (vec (flatten board))
        rows (map-indexed create-table-data board)]
     (vec (partition board-size board-size [] rows))))

(defn winner-header [board]
  (let [winner  (winner board)]
    (if (not= winner nil)
      (str "<h2>"winner"!</h2>")
      (str "<h2>Select move</h2>"))))

(defn form-path [board]
  (let [winner  (winner board)]
    (if (not= winner nil)
      (str "<form action=\"/\" method=\"GET\">")
      (str "<form action=\"/move\" method=\"GET\">"))))

(defn form-submit [board]
  (let [winner  (winner board)]
    (if (= winner nil)
      (str "<input type=\"submit\" value=\"Submit Move\" name=\"move\">")
      (str ""))))

(defn get-board-view [board]
  (let [table-data (build-rows board)]
  (str "<html>"
       "<head>"
         "<style>"
            (slurp "resources/board-style.css")
         "</style>"
       "</head>"
       "<h1>Unbeatable Tic-Tac-Toe</h1>"
       (winner-header board)
          "<body>"
            "<form action=\"/move\" method=\"POST\">"
              "<table><tr>"
                 (str/join (get table-data 0))
                 "</tr><tr>"
                 (str/join  (get table-data 1))
                 "</tr><tr>"
                 (str/join (get table-data 2))
                 "</tr></table>"
                 (form-submit board)"<br>"
            "</form>"
            "<p>"
              "<a href="\/">New Game</a>"
            "</p>"
          "</body>"
        "</html>")))
