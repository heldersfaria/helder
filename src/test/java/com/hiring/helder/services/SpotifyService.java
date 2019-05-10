//package com.hiring.helder.services;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import com.hiring.helder.enums.GenerosEnum;
//import com.hiring.helder.exceptions.DiscoException;
//import com.wrapper.spotify.SpotifyApi;
//import com.wrapper.spotify.exceptions.SpotifyWebApiException;
//import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
//import com.wrapper.spotify.model_objects.specification.Artist;
//import com.wrapper.spotify.model_objects.specification.Paging;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.Set;
//import java.util.TreeSet;
//
//import static com.wrapper.spotify.enums.ModelObjectType.ALBUM;
//import static com.wrapper.spotify.enums.ModelObjectType.ARTIST;
//
//@Component
//public class SpotifyService implements ApplicationListener<ContextRefreshedEvent> {
//
//    @Autowired
//    private SpotifyApi spotifyApi;
//
//    @Autowired
//    private DiscoVinilService discoVinilService;
//
//    private static final String qGenre = "genre:";
//    private static final String qArtist = "artist:";
//
//    private void buildDiscos(String genero) throws DiscoException {
//
//        try {
//            Paging<Artist> artistas = spotifyApi.searchItem(qGenre + genero, ARTIST.getType()).limit(50).build().execute().getArtists();
//
//            JsonObject jsonArtistas = (JsonObject) new JsonParser().parse(new ObjectMapper().writeValueAsString(artistas));
//            JsonArray items = jsonArtistas.getAsJsonArray("items");
//
//            Set<String> names = new TreeSet<>();
//
//            for (int i = 0; i < items.size(); i++) {
//
//                String artist = "\"" + items.get(i).getAsJsonObject().get("name").getAsString() + "\"";
//
//                getAlbunsByArtist(genero, artist, names);
//
//                if (names.size() == 50) {
//                    break;
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new DiscoException(e.getCause());
//        }
//    }
//
//    private void getAlbunsByArtist(String genero, String artist, Set<String> names) throws SpotifyWebApiException, IOException {
//
//
//        Paging<AlbumSimplified> albums = spotifyApi.searchItem(qArtist + artist, ALBUM.getType()).limit(50).build().execute().getAlbums();
//
//        JsonObject jsonAlbums = (JsonObject) new JsonParser().parse(new ObjectMapper().writeValueAsString(albums));
//
//        JsonArray itemsAlbums = jsonAlbums.getAsJsonArray("items");
//
//        for (int i = 0; i < itemsAlbums.size(); i++) {
//
//            JsonObject album = itemsAlbums.get(i).getAsJsonObject();
//
//            String nome = album.get("name").getAsString();
//            String id = album.get("id").getAsString();
//
//            discoVinilService.salvarDisco(id, genero, nome);
//
//            names.add(nome);
//
//            if (names.size() == 50) {
//                return;
//            }
//        }
//    }
//
//
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//
//        if (!this.discoVinilService.hasData()) {
//            try {
//                for (GenerosEnum genero : GenerosEnum.values()) {
//                    buildDiscos(genero.getNome());
//                }
//            } catch (DiscoException e) {
//                e.printStackTrace();
//                return;
//            }
//        }
//    }
//}
