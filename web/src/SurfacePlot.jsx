import Plot from "react-plotly.js";

export default function SurfacePlot({ path }) {

    const range = [...Array(50).keys()].map(i => -1 + i * 0.08);

    const z = range.map(x =>
        range.map(y =>
            2.5 * Math.pow(x * x - y, 2) + Math.pow(1 - x, 2)
        )
    );

    const xs = path.map(p => p[0]);
    const ys = path.map(p => p[1]);
    const zs = path.map(p => p[2]);

    return (
        <Plot
            data={[
                {
                    type: "surface",
                    x: range,
                    y: range,
                    z: z,
                    colorscale: "YlOrBr",
                    opacity: 0.9
                },
                {
                    type: "scatter3d",
                    mode: "lines+markers",
                    x: xs,
                    y: ys,
                    z: zs,
                    line: { width: 6, color: "red" },
                    marker: { size: 4, color: "red" }
                }
            ]}
            layout={{
                title: "Hooke–Jeeves optimization path",
                autosize: true,
                height: 500,
                scene: {
                    xaxis: { title: "x" },
                    yaxis: { title: "y" },
                    zaxis: { title: "f(x,y)" }
                }
            }}
        />
    );
}
