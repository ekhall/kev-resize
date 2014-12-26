(defproject kev-resize "0.1.0-SNAPSHOT"
  :description "Resize Photos for Nix Frames 1920x1080"
  :url "http://absinthe.org"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [image-resizer "0.1.6"]
                 [clj-time "0.8.0"]
                 [io.aviso/pretty "0.1.13"]]
  :main kev-resize.core)
