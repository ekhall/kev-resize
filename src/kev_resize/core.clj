(ns kev-resize.core
  (:require [clojure.java.io :refer :all]
            [clj-time.core :as time]
            [clj-time.coerce :as tc]
            [image-resizer.crop :refer :all]
            [image-resizer.resize :refer :all]
            [image-resizer.format :as format]
            [image-resizer.core :refer :all]
            [image-resizer.scale-methods :refer :all]
            [image-resizer.util :refer [buffered-image]]))

(defmacro dlet [bindings & body]
  `(let [~@(mapcat (fn [[n v]]
                       (if (or (vector? n) (map? n))
                           [n v]
                         [n v '_ `(println (name '~n) ":" ~v)]))
                   (partition 2 bindings))]
     ~@body))

(defn fixed-length-string
  ([] (fixed-length-string 8))
  ([n]
     (let [chars (map char (range 97 122))
           mystring (take n (repeatedly #(rand-nth chars)))]
       (reduce str mystring))))

(defn ultra-resize [image width height]
  ((resize-fn width height ultra-quality) image))

(defn nix-resize [new-width new-height]
  "Resize a photo to new width, then with equal
  crops from top and bottom to specified height,
  preserving perspective"
  (fn [image]
    (let [buffered (buffered-image image)
          [original-width original-height] (dimensions buffered)
          [_ crop-height] (dimensions ((resize-width-fn new-width) image))
          vertical-pad (int (/ (- crop-height new-height) 2))]
          (format/as-file
           (crop-from
            (ultra-resize image new-width crop-height) 0 vertical-pad new-width new-height)
           (str "/Users/hall/Desktop/Nix/kev-resize/o/" (fixed-length-string) ".jpg" ))
          (println "Wrote File...."))))

(def nix-make-image (nix-resize 1920 1080))

(defn -main [& args]
  (let [names-vector (vec args)
        files-vector (map file names-vector)]
    (doall
     (clojure.core/pmap nix-make-image files-vector))))
