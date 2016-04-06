(ns ttt-clj-java-server.board-builder
  (:require [clojure.string :as str]
            [ttt-clj-java-server.helpers.params-parser :refer :all]
            [tic-tac-toe.board :refer :all]
            [tic-tac-toe.game :refer :all]))

(defn add-move [board move-selection marker]
  (if (moveopen? board move-selection)
    (let [matrix-position (matrix-convrt move-selection 3)]
      (move matrix-position marker board))))

(defn update-position [board position]
  (let[selection (str/split position #"=")
       marker    (first selection)
       move-selection (read-string (last selection))]
    (add-move board move-selection marker)))

(defn fill-board  [board taken-positions]
  (reduce  update-position board taken-positions))

(defn current-board [params]
  (let [positions (get-board-positions params)
        board (create-empty-board 3)
        current-board (fill-board board  positions)]
    current-board))

(defn create-table-data [index marker]
  (let [position (inc index)]
    (if (= marker "_")
      (str "<td><input type=\"radio\" value=\"" position "\" name=\"openPosition\"></td>")
      (str"<td>" marker "<input type=\"hidden\" value=\"" position "\" name="\" marker "\"></td>"))))

(defn build-rows [board]
  (let [board-size (count board)
        board (vec (flatten board))
        rows  (map-indexed create-table-data board)]
     (vec (partition board-size board-size [] rows))))

(defn get-board-view [table-data]
  (prn table-data)
  (str "<html><head>Tic-tac-toe</head>"
         "<h1>Unbeatable Tic-Tac-Toe</h1>"
         "<body>"
           "<form action=\"/move\" method=\"GET\">"
             "<table>"
              " <tr>"
                (str/join "\n" (get table-data 0))
               "</tr>"
               "<tr>"
               (str/join "\n" (get table-data 1))
              "</tr>"
              " <tr>"
                (str/join "\n" (get table-data 2))
              "</tr>"
             "</table>"
            "<input type=\"submit\" value=\"Submit\" name=\"move\">"
           "</form>"
         "</body>"
        "</html>"))
