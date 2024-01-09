import * as React from 'react';
import Box from '@mui/material/Box';
import ImageList from '@mui/material/ImageList';
import ImageListItem from '@mui/material/ImageListItem';
import ImageListItemBar from '@mui/material/ImageListItemBar';
import { Grid, Typography } from '@mui/material';

export default function TitlebarBelowMasonryImageList() {
    return (
        <Grid>
            <Typography variant="h5" sx={{marginTop: 15, marginLeft: 17}}>Our Testimonials</Typography>
            <Box sx={{ width: 500, height: 500, overflowY: 'scroll'}}>
                <ImageList variant="masonry" cols={3} gap={8}>
                    {itemData.map((item) => (
                        <ImageListItem key={item.img}>
                            <img
                                srcSet={`${item.img}?w=248&fit=crop&auto=format&dpr=2 2x`}
                                src={`${item.img}?w=248&fit=crop&auto=format`}
                                alt={item.title}
                                loading="lazy"
                            />
                            <ImageListItemBar position="below" title={item.author} />
                        </ImageListItem>
                    ))}
                </ImageList>
            </Box>
        </Grid>
    );
}

const itemData = [
    {
        img: 'https://images.unsplash.com/photo-1600320254374-ce2d293c324e',
        title: 'Bed',
        author: 'Paul Hanaoka',
    },
    {
        img: 'https://images.unsplash.com/photo-1482029255085-35a4a48b7084',
        title: 'Books',
        author: 'Pavel Nekoranec',
    },
    {
        img: 'https://images.unsplash.com/photo-1598769398698-bab7f1b4cadd',
        title: 'Sink',
        author: 'Charles Deluvio',
    },
    {
        img: 'https://plus.unsplash.com/premium_photo-1661767506945-f5e8a2ba70af?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTd8fGNhYiUyMGRyaXZlcnxlbnwwfHwwfHx8MA%3D%3D',
        title: 'Sink',
        author: 'Charles Deluvio',
    },
    {
        img: 'https://plus.unsplash.com/premium_photo-1661510316006-45fb0f58f5d9?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fGNhYiUyMGRyaXZlcnxlbnwwfHwwfHx8MA%3D%3D',
        title: 'Sink',
        author: 'Charles Deluvio',
    },
    {
        img: 'https://images.unsplash.com/photo-1613638377394-281765460baa?q=80&w=1965&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
        title: 'Sink',
        author: 'Charles Deluvio',
    },
    {
        img: 'https://plus.unsplash.com/premium_photo-1682092280716-d1bc7ccb1947?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
        title: 'Sink',
        author: 'Charles Deluvio',
    },
    {
        img: 'https://images.unsplash.com/photo-1600320254374-ce2d293c324e',
        title: 'Bed',
        author: 'Paul Hanaoka',
    },
    {
        img: 'https://images.unsplash.com/photo-1482029255085-35a4a48b7084',
        title: 'Books',
        author: 'Pavel Nekoranec',
    },
    {
        img: 'https://images.unsplash.com/photo-1598769398698-bab7f1b4cadd',
        title: 'Sink',
        author: 'Charles Deluvio',
    },
    {
        img: 'https://plus.unsplash.com/premium_photo-1661767506945-f5e8a2ba70af?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTd8fGNhYiUyMGRyaXZlcnxlbnwwfHwwfHx8MA%3D%3D',
        title: 'Sink',
        author: 'Charles Deluvio',
    },
    {
        img: 'https://plus.unsplash.com/premium_photo-1661510316006-45fb0f58f5d9?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fGNhYiUyMGRyaXZlcnxlbnwwfHwwfHx8MA%3D%3D',
        title: 'Sink',
        author: 'Charles Deluvio',
    },
];
